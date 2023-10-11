package com.neqabty.healthcare.syndicateservices.data.api


import com.neqabty.healthcare.syndicateservices.data.model.SyndicateServicesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface SyndicateServicesApi {

    @GET("api/services")
    suspend fun getSyndicateServices(@Query("filter{entity.code}") entityCode: String): SyndicateServicesResponse //@Query("filter{service_category.name}") serviceCategory: String

}