package com.neqabty.shealth.sustainablehealth.search.data.source

import com.neqabty.shealth.sustainablehealth.search.data.api.SearchApi
import com.neqabty.shealth.sustainablehealth.search.data.model.MedicalProviderModel
import com.neqabty.shealth.sustainablehealth.search.data.model.SearchBody
import com.neqabty.shealth.sustainablehealth.search.data.model.filter.FiltersModel
import com.neqabty.shealth.sustainablehealth.search.data.model.packages.PackageModel
import com.neqabty.shealth.sustainablehealth.search.data.model.search.ProvidersModel
import javax.inject.Inject

class SearchDS @Inject constructor(private val searchApi: SearchApi) {

    suspend fun getHealthCareProviders(): List<MedicalProviderModel> {
        return searchApi.getMedicalProviders().data.data
    }

    suspend fun searchMedicalProviders(body: SearchBody): List<ProvidersModel> {
        return searchApi.searchMedicalProviders(body).data.data
    }

    suspend fun getFilters(): FiltersModel {
        return searchApi.getFilters().data
    }

    suspend fun getPackages(code: String): List<PackageModel> {
        return searchApi.getPackages(code).data
    }
}