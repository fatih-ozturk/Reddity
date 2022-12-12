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
package com.reddity.app.network.di

import com.reddity.app.auth.AuthPersistManager
import com.reddity.app.auth.ReddityAuthManager
import com.reddity.app.network.api.AccountApi
import com.reddity.app.network.api.PostApi
import com.reddity.app.network.model.NetworkListingEnveloped
import com.reddity.app.network.model.NetworkListingKind
import com.reddity.app.network.model.NetworkListingType
import com.reddity.app.network.utils.ReddityAuthInterceptor
import com.reddity.app.network.utils.ReddityAuthenticator
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttp: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder().client(okHttp).baseUrl("https://reddit.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        reddityAuthInterceptor: ReddityAuthInterceptor,
        reddityAuthenticator: ReddityAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(reddityAuthInterceptor)
            .addInterceptor(loggingInterceptor).authenticator(reddityAuthenticator).build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(
        PolymorphicJsonAdapterFactory.of(
            NetworkListingKind::class.java, "kind"
        ).withSubtype(
            NetworkListingEnveloped::class.java, NetworkListingType.LINK.name
        )
    ).build()

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    internal fun provideReddityAuthInterceptor(
        authPersistManager: AuthPersistManager,
    ): ReddityAuthInterceptor {
        return ReddityAuthInterceptor(authPersistManager)
    }

    @Provides
    @Singleton
    internal fun provideReddityAuthenticator(
        reddityAuthManager: ReddityAuthManager,
        authPersistManager: AuthPersistManager
    ): ReddityAuthenticator {
        return ReddityAuthenticator(
            reddityAuthManager, authPersistManager
        )
    }

    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi = retrofit.create(AccountApi::class.java)

    @Provides
    @Singleton
    fun provideHomeApi(retrofit: Retrofit): PostApi = retrofit.create(PostApi::class.java)
}
