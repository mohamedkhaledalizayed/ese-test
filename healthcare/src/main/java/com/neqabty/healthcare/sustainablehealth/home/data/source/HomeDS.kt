package com.neqabty.healthcare.sustainablehealth.home.data.source

import com.neqabty.healthcare.sustainablehealth.home.data.api.HomeApi
import com.neqabty.healthcare.sustainablehealth.home.data.model.about.AboutModel
import javax.inject.Inject

class HomeDS @Inject constructor(private val homeApi: HomeApi) {
    suspend fun getAboutList(): List<AboutModel> {
        return homeApi.getAboutList().data
    }
}