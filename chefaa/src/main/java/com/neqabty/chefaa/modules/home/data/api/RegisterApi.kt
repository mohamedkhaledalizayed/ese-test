package com.neqabty.chefaa.modules.home.data.api

import com.neqabty.chefaa.modules.ChefaaResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisterApi {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("mobile") mobile: String,
        @Field("user_number") userId: String,
        @Field("country_code") countryCode: String,
        @Field("national_id") nationalId: String,
    ): ChefaaResponse<Unit>
}