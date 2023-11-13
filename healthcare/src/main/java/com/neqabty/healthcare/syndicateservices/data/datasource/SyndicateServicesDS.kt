package com.neqabty.healthcare.syndicateservices.data.datasource


import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.syndicateservices.data.api.SyndicateServicesApi
import com.neqabty.healthcare.syndicateservices.data.model.SyndicateService
import javax.inject.Inject

class SyndicateServicesDS @Inject constructor(private val syndicateServicesApi: SyndicateServicesApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getSyndicateServices(entityCode: String, serviceCategory: String): List<SyndicateService> {
        return syndicateServicesApi.getSyndicateServices(token = preferencesHelper.token, entityCode).syndicateServices
    }

}