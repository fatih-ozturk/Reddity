/*
 * Copyright 2022 Fatih OZTURK
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reddity.app.auth

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toUri
import com.reddity.app.base.inject.ApplicationId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.ResponseTypeValues
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ReddityAuthModule {

    @Provides
    @Named("reddit-client-id")
    fun provideRedditClientId(): String = BuildConfig.REDDIT_CLIENT_ID

    @Provides
    @Named("reddit-client-secret")
    fun provideRedditClientSecret(): String = BuildConfig.REDDIT_CLIENT_SECRET

    @Singleton
    @Provides
    fun provideAuthConfig(): AuthorizationServiceConfiguration {
        return AuthorizationServiceConfiguration(
            Uri.parse("https://www.reddit.com/api/v1/authorize"),
            Uri.parse("https://www.reddit.com/api/v1/access_token")
        )
    }

    @Singleton
    @Provides
    @Named("auth")
    fun provideAuthSharedPrefs(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("reddity-auth", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideAuthRequest(
        serviceConfig: AuthorizationServiceConfiguration,
        @Named("reddit-client-id") clientId: String,
        @Named("reddit-auth-redirect-uri") redirectUri: String
    ): AuthorizationRequest {
        return AuthorizationRequest.Builder(
            serviceConfig,
            clientId,
            ResponseTypeValues.CODE,
            redirectUri.toUri()
        )
            .setAdditionalParameters(buildMap { put("duration", "temporary") })
            .setScope(
                "identity edit flair history " +
                    "modconfig modflair modlog " +
                    "modposts modwiki mysubreddits " +
                    "privatemessages read report " +
                    "save submit subscribe vote wikiedit wikiread"
            )
            .build()
    }

    @Singleton
    @Named("reddit-auth-redirect-uri")
    @Provides
    fun provideAuthRedirectUri(
        @ApplicationId applicationId: String
    ): String = "$applicationId://auth/oauth2callback"

    @Singleton
    @Provides
    fun provideClientAuth(
        @Named("reddit-client-secret") clientSecret: String
    ): ClientAuthentication {
        return ClientSecretBasic(clientSecret)
    }

    @Singleton
    @Provides
    fun provideAuthManager(
        @ApplicationContext context: Context,
        authManager: AuthManager,
        clientAuth: ClientAuthentication,
        requestProvider: AuthorizationRequest
    ): ReddityAuthManager {
        return ReddityAuthManagerImpl(context, authManager, clientAuth, requestProvider)
    }
}
