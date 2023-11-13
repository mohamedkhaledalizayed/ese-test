package com.neqabty.healthcare.packages.packageslist.data.datasource

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.packages.packageslist.data.api.PackagesApi
import com.neqabty.healthcare.packages.packageslist.data.model.InsuranceBody
import com.neqabty.healthcare.packages.packageslist.data.model.PackageModel
import com.neqabty.healthcare.packages.packageslist.data.model.insurance.InsuranceModelList
import javax.inject.Inject

class PackagesDS @Inject constructor(private val packagesApi: PackagesApi, private val preferencesHelper: PreferencesHelper){

    suspend fun getPackages(code: String): List<PackageModel> {
        return packagesApi.getPackages(token = preferencesHelper.token, code).data
    }

    suspend fun getInsuranceDocs(packageId: String): InsuranceModelList {
        return packagesApi.getInsuranceDocs(token = preferencesHelper.token, InsuranceBody(packageId))
    }

}