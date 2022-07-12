/*
 * Copyright 2017 Fatih OZTURK
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
package com.bireddit.app.di.app

import android.app.Application
import android.content.Context
import com.bireddit.app.BuildConfig
import com.bireddit.app.base.inject.ApplicationId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @ApplicationId
    @Provides
    fun provideApplicationId(application: Application): String = application.packageName

    @Provides
    @Singleton
    @Named("cache")
    fun provideCacheDir(
        @ApplicationContext context: Context
    ): File = context.cacheDir

    @Provides
    @Named("reddit-client-id")
    fun provideRedditClientId(): String = BuildConfig.REDDIT_CLIENT_ID

    @Provides
    @Named("reddit-client-secret")
    fun provideRedditClientSecret(): String = BuildConfig.REDDIT_CLIENT_SECRET
}
