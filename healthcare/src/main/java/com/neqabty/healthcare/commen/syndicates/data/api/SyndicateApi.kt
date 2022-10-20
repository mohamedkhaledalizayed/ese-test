package com.neqabty.healthcare.commen.syndicates.data.api

import com.neqabty.healthcare.commen.syndicates.data.model.SyndicateResponse
import retrofit2.http.GET

interface SyndicateApi {
    @GET("entities/")
    suspend fun getSyndicates(): SyndicateResponse
}