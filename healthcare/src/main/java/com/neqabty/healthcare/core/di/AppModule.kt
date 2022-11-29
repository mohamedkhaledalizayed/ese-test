package com.neqabty.healthcare.core.di

import android.app.Application
import android.content.SharedPreferences
import com.neqabty.healthcare.core.data.Constants.ENCRYPT_KEY
import com.neqabty.healthcare.core.data.Constants.PREFS_FILE
import com.neqabty.healthcare.core.data.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
//        return com.securepreferences.SecurePreferences(application, ENCRYPT_KEY, PREFS_FILE)
        val sharedPrefsFile: String = PREFS_FILE
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            sharedPrefsFile,
            mainKeyAlias,
            application,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences
    }

    @Singleton
    @Provides
    fun providePreferencesHelper(sharedPref: com.securepreferences.SecurePreferences): PreferencesHelper {
        return PreferencesHelper(preferences = sharedPref)
    }

}