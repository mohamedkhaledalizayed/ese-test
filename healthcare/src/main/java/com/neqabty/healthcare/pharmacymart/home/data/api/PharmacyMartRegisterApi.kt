package com.neqabty.healthcare.pharmacymart.home.data.api


import com.neqabty.healthcare.chefaa.orders.data.model.*
import com.neqabty.healthcare.pharmacymart.home.data.model.RegisterModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface PharmacyMartRegisterApi {

    @FormUrlEncoded
    @POST("healthcare/api/v1/pharmacy/register")
    suspend fun register(
        @Field("mobile") mobile: String,
        @Field("name") name: String,
        @Field("entity_code") entityCode: String
    ): RegisterModel

}