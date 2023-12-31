package com.glambiase.caloriestracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.glambiase.core.data.preferences.DefaultPreferences
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.core.domain.use_case.FilterDigitsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences =
        app.getSharedPreferences("shared_preferences", MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences =
        DefaultPreferences(sharedPreferences)

    @Provides
    @Singleton
    fun provideFilterDigitsUseCase(): FilterDigitsUseCase =
        FilterDigitsUseCase()
}