package com.neqabty.healthcare.mypackages.packages.data.datasource

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.mypackages.packages.data.api.MyPackagesApi
import com.neqabty.healthcare.mypackages.packages.data.model.PackagesBody
import com.neqabty.healthcare.mypackages.packages.data.model.ProfileModel
import javax.inject.Inject

class MyPackagesDS @Inject constructor(private val myPackagesApi: MyPackagesApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun getMyPackages(phone: String): ProfileModel {
        return myPackagesApi.getMyPackages(token = "Token ${sharedPreferences.token}", PackagesBody(mobile = phone))
    }

}