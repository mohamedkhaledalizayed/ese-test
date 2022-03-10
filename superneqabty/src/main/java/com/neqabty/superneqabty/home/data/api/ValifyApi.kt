package com.neqabty.superneqabty.home.data.api

import com.neqabty.superneqabty.home.data.model.valify.GetToken
import retrofit2.http.GET


interface ValifyApi {

    @GET("valify/token")
    suspend fun getToken(): GetToken

}