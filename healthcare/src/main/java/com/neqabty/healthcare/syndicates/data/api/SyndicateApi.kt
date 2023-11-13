package com.neqabty.healthcare.syndicates.data.api

import com.neqabty.healthcare.syndicates.data.model.SyndicateResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SyndicateApi {
    @GET("api/entities/")
    suspend fun getSyndicates(@Query("filter{type.name}") type: String = "syndicate"): SyndicateResponse
}