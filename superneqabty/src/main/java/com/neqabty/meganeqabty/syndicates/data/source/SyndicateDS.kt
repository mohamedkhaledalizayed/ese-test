package com.neqabty.meganeqabty.syndicates.data.source

import com.neqabty.meganeqabty.syndicates.data.api.SyndicateApi
import com.neqabty.meganeqabty.syndicates.data.model.SyndicateModel
import javax.inject.Inject

class SyndicateDS @Inject constructor(private val syndicateApi: SyndicateApi) {
    suspend fun getSyndicates(): List<SyndicateModel> {
        return syndicateApi.getSyndicates().syndicateModels
    }
}