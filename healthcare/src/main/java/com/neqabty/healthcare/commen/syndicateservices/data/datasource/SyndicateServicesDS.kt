package com.neqabty.healthcare.commen.syndicateservices.data.datasource


import com.neqabty.healthcare.commen.syndicateservices.data.api.SyndicateServicesApi
import com.neqabty.healthcare.commen.syndicateservices.data.model.SyndicateService
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject

class SyndicateServicesDS @Inject constructor(private val syndicateServicesApi: SyndicateServicesApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getSyndicateServices(entityCode: String, serviceCategory: String): List<SyndicateService> {
        return syndicateServicesApi.getSyndicateServices(entityCode).syndicateServices
    }

}