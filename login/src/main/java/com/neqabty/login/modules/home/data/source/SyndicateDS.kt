package com.neqabty.login.modules.home.data.source

import com.neqabty.login.modules.home.data.api.SyndicateApi
import com.neqabty.login.modules.home.data.model.SyndicateModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SyndicateDS @Inject constructor(private val syndicateApi: SyndicateApi) {
    suspend fun getSyndicates(): List<SyndicateModel> {
        return syndicateApi.getSyndicates("syndicate").syndicateModels
    }
}