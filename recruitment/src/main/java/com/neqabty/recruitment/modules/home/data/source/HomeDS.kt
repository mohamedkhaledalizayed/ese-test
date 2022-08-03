package com.neqabty.recruitment.modules.home.data.source

import com.neqabty.recruitment.modules.home.data.api.HomeApi
import javax.inject.Inject

class HomeDS @Inject constructor(private val homeApi: HomeApi) {

    suspend fun recruitment(): String{
        return homeApi.recruitment()
    }

}