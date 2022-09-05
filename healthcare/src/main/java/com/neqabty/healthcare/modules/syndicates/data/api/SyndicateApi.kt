package com.neqabty.healthcare.modules.syndicates.data.api

import com.neqabty.healthcare.modules.syndicates.data.model.SyndicateResponse
import retrofit2.http.GET

interface SyndicateApi {
    @GET("entities/")
    suspend fun getSyndicates(): SyndicateResponse
}