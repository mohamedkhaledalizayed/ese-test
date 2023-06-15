package com.neqabty.healthcare.commen.syndicateservices.data.api


import com.neqabty.healthcare.commen.syndicateservices.data.model.SyndicateServicesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface SyndicateServicesApi {

    @GET("services")
    suspend fun getSyndicateServices(@Query("filter{entity.code}") entityCode: String): SyndicateServicesResponse //@Query("filter{service_category.name}") serviceCategory: String

}