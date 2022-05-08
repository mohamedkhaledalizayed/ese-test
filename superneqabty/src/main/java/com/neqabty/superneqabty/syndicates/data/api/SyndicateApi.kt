package com.neqabty.superneqabty.syndicates.data.api

import com.neqabty.superneqabty.syndicates.data.model.SyndicateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SyndicateApi {
    @GET("api/entities/")
    suspend fun getSyndicates(@Query("filter{type.name}") syndicate: String): SyndicateResponse
}