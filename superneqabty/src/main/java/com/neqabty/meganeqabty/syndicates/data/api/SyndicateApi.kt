package com.neqabty.meganeqabty.syndicates.data.api

import com.neqabty.meganeqabty.syndicates.data.model.SyndicateResponse
import retrofit2.http.GET

interface SyndicateApi {
    @GET("api/entities/")
    suspend fun getSyndicates(): SyndicateResponse
}