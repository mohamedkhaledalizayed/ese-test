package com.neqabty.healthcare.medicalnetwork.data.source

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.medicalnetwork.data.api.SearchApi
import com.neqabty.healthcare.medicalnetwork.data.model.AreaBody
import com.neqabty.healthcare.medicalnetwork.data.model.MedicalProviderModel
import com.neqabty.healthcare.medicalnetwork.data.model.SearchBody
import com.neqabty.healthcare.medicalnetwork.data.model.area.AreaModel
import com.neqabty.healthcare.medicalnetwork.data.model.filter.FiltersModel
import com.neqabty.healthcare.medicalnetwork.data.model.search.ProvidersModel
import javax.inject.Inject

class SearchDS @Inject constructor(private val searchApi: SearchApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getMedicalProviderDetails(id: String): ProvidersModel {
        return searchApi.getMedicalProviderDetails(token = preferencesHelper.token, id).data
    }

    suspend fun getHealthCareProviders(): List<MedicalProviderModel> {
        return searchApi.getMedicalProviders(token = preferencesHelper.token).data.data
    }

    suspend fun searchMedicalProviders(body: SearchBody): List<ProvidersModel> {
        return searchApi.searchMedicalProviders(token = preferencesHelper.token, body).data.data
    }

    suspend fun getFilters(): FiltersModel {
        return searchApi.getFilters(token = preferencesHelper.token).data
    }

    suspend fun getAreasByGov(id: Int): List<AreaModel> {
        return searchApi.getAreasByGov(token = preferencesHelper.token, body = AreaBody(governorate_id = id)).data
    }

}