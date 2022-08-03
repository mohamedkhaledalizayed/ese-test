package com.neqabty.recruitment.modules.home.data.api

import retrofit2.http.GET

interface HomeApi {

    @GET("")
    suspend fun recruitment(): String
}