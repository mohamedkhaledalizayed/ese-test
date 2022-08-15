package com.neqabty.healthcare.modules.syndicates.data.source

import com.neqabty.healthcare.modules.syndicates.data.api.SyndicateApi
import com.neqabty.healthcare.modules.syndicates.data.model.SyndicateModel
import javax.inject.Inject

class SyndicateDS @Inject constructor(private val syndicateApi: SyndicateApi) {
    suspend fun getSyndicates(): List<SyndicateModel> {
        return syndicateApi.getSyndicates().syndicateModels
    }
}