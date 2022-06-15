package com.neqabty.healthcare.modules.search.data.source

import com.neqabty.healthcare.modules.search.data.api.SearchApi
import com.neqabty.healthcare.modules.search.data.model.MedicalProviderModel
import javax.inject.Inject

class SearchDS @Inject constructor(private val searchApi: SearchApi) {
    suspend fun getHealthCareProviders(): List<MedicalProviderModel> {
        return searchApi.getMedicalProviders().data.data
    }
}