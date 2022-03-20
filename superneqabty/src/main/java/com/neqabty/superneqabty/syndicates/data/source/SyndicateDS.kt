package com.neqabty.superneqabty.syndicates.data.source

import com.neqabty.superneqabty.syndicates.data.api.SyndicateApi
import com.neqabty.superneqabty.syndicates.data.model.SyndicateModel
import javax.inject.Inject

class SyndicateDS @Inject constructor(private val syndicateApi: SyndicateApi) {
    suspend fun getSyndicates(): List<SyndicateModel> {
        return syndicateApi.getSyndicates("syndicate").syndicateModels
    }
}