package com.neqabty.healthcare.modules.home.data.source

import com.neqabty.healthcare.modules.home.data.api.HomeApi
import com.neqabty.healthcare.modules.home.data.model.about.AboutListModel
import com.neqabty.healthcare.modules.home.data.model.about.AboutModel
import javax.inject.Inject

class HomeDS @Inject constructor(private val homeApi: HomeApi) {
    suspend fun getAboutList(): List<AboutModel> {
        return homeApi.getAboutList().data
    }
}