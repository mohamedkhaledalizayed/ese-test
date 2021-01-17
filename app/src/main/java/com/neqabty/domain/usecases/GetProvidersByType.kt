package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProviderEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetProvidersByType @Inject constructor(
    transformer: Transformer<List<ProviderEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<ProviderEntity>>(transformer) {

    companion object {
        private const val PARAM_PROVIDER_TYPE_ID = "param:providerTypeId"
        private const val PARAM_GOV_ID = "param:govId"
        private const val PARAM_AREA_ID = "param:areaId"
        private const val PARAM_PROVIDER_NAME = "param:providerName"
        private const val PARAM_PROFESSION_ID = "param:professionId"
        private const val PARAM_DEGREE_ID = "param:degreeId"
    }

    fun getProvidersByType(
        providerTypeId: String,
        govId: String,
        areaId: String,
        providerName: String? = "",
        professionID: String? = "",
        degreeID: String? = ""
    ): Observable<List<ProviderEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_PROVIDER_TYPE_ID] = providerTypeId
        data[PARAM_GOV_ID] = govId
        data[PARAM_AREA_ID] = areaId
        providerName?.let { data[PARAM_PROVIDER_NAME] = it }
        professionID?.let { data[PARAM_PROFESSION_ID] = it }
        degreeID?.let { data[PARAM_DEGREE_ID] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ProviderEntity>> {
        val providerTypeId = data?.get(PARAM_PROVIDER_TYPE_ID) as String
        val govId = data?.get(PARAM_GOV_ID) as String
        val areaId = data?.get(PARAM_AREA_ID) as String
        val providerName = data?.get(PARAM_PROVIDER_NAME) as String
        val professionID = data?.get(PARAM_PROFESSION_ID) as String
        val degreeID = data?.get(PARAM_DEGREE_ID) as String
        return neqabtyRepository.getProvidersByType(providerTypeId, govId, areaId, providerName, professionID, degreeID)
    }
}