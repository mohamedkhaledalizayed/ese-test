package com.neqabty.healthcare.sustainablehealth.home.data.source

import com.neqabty.healthcare.sustainablehealth.home.data.api.HomeApi
import com.neqabty.healthcare.sustainablehealth.home.data.model.about.AboutModel
import com.neqabty.healthcare.sustainablehealth.home.data.model.about.packages.PackageModel
import javax.inject.Inject

class HomeDS @Inject constructor(private val homeApi: HomeApi) {

    suspend fun getAboutList(): List<AboutModel> {
        return homeApi.getAboutList().data
    }

    suspend fun getPackages(code: String): List<PackageModel> {
        return homeApi.getPackages(code).data
    }

}