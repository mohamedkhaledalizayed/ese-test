package com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.source

import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.api.SearchApi
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.AreaBody
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.MedicalProviderModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.SearchBody
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.area.AreaModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.filter.FiltersModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.packages.PackageModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search.ProvidersModel
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

    suspend fun getAreasByGov(id: Int): List<AreaModel> {
        return searchApi.getAreasByGov(body = AreaBody(governorate_id = id)).data
    }

    suspend fun getPackages(code: String): List<PackageModel> {
        return searchApi.getPackages(code).data
    }
}