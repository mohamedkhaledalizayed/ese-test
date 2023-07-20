package com.neqabty.healthcare.packages.packageslist.data.datasource

import com.neqabty.healthcare.packages.packageslist.data.api.PackagesApi
import com.neqabty.healthcare.packages.packageslist.data.model.PackageModel
import javax.inject.Inject

class PackagesDS @Inject constructor(private val packagesApi: PackagesApi){

    suspend fun getPackages(code: String): List<PackageModel> {
        return packagesApi.getPackages(code).data
    }

}