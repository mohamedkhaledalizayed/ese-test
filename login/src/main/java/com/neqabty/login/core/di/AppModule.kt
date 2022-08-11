package com.neqabty.login.core.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    @Singleton
//    @Provides
//    fun provideSharedPreferences(application: Application): SharedPreferences {
//        return PreferenceManager.getDefaultSharedPreferences(application)
//    }
//
//    @Named("login")
//    @Provides
//    fun providePreferencesHelper(sharedPref: SharedPreferences): PreferencesHelper {
//        return PreferencesHelper(preferences = sharedPref)
//    }

}