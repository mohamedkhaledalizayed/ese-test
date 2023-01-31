package com.neqabty.healthcare.sustainablehealth.search.data.source

import com.neqabty.healthcare.sustainablehealth.search.data.api.SearchApi
import com.neqabty.healthcare.sustainablehealth.search.data.model.AreaBody
import com.neqabty.healthcare.sustainablehealth.search.data.model.MedicalProviderModel
import com.neqabty.healthcare.sustainablehealth.search.data.model.SearchBody
import com.neqabty.healthcare.sustainablehealth.search.data.model.area.AreaListModel
import com.neqabty.healthcare.sustainablehealth.search.data.model.area.AreaModel
import com.neqabty.healthcare.sustainablehealth.search.data.model.filter.FiltersModel
import com.neqabty.healthcare.sustainablehealth.home.data.model.about.packages.PackageModel
import com.neqabty.healthcare.sustainablehealth.search.data.model.search.ProvidersModel
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

}