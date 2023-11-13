package com.neqabty.healthcare.syndicates.data.source

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.syndicates.data.api.SyndicateApi
import com.neqabty.healthcare.syndicates.data.model.SyndicateModel
import javax.inject.Inject

class SyndicateDS @Inject constructor(private val syndicateApi: SyndicateApi, private val preferencesHelper: PreferencesHelper) {
    suspend fun getSyndicates(): List<SyndicateModel> {
        return syndicateApi.getSyndicates().syndicateModels
    }
}