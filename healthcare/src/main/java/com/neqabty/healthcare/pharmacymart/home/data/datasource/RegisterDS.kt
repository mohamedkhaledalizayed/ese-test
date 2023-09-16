package com.neqabty.healthcare.pharmacymart.home.data.datasource



import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.pharmacymart.home.data.api.PharmacyMartRegisterApi
import com.neqabty.healthcare.pharmacymart.home.data.model.RegisterModel
import javax.inject.Inject

class RegisterDS @Inject constructor(private val registerApi: PharmacyMartRegisterApi,
                                     private val sharedPreferencesHelper: PreferencesHelper) {

    suspend fun registerUser(): RegisterModel {
        return registerApi.register(mobile = sharedPreferencesHelper.mobile,
            name = sharedPreferencesHelper.name,
            entityCode = sharedPreferencesHelper.code)
    }

}