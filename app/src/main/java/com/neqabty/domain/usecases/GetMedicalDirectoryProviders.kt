package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalDirectoryProviderEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalDirectoryProviders @Inject constructor(
    transformer: Transformer<List<MedicalDirectoryProviderEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<MedicalDirectoryProviderEntity>>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_PROVIDER_TYPE_ID = "param:providerTypeId"
        private const val PARAM_GOV_ID = "param:govId"
        private const val PARAM_AREA_ID = "param:areaId"
        private const val PARAM_PROVIDER_NAME = "param:providerName"
        private const val PARAM_SPECIALIZATION_ID = "param:specializationId"
    }

    fun getProviders(
        mobileNumber: String,
        providerTypeId: String,
        govId: String,
        areaId: String,
        providerName: String,
        specializationId: String
    ): Observable<List<MedicalDirectoryProviderEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobileNumber
        data[PARAM_PROVIDER_TYPE_ID] = providerTypeId
        data[PARAM_GOV_ID] = govId
        data[PARAM_AREA_ID] = areaId
        providerName?.let { data[PARAM_PROVIDER_NAME] = it }
        specializationId?.let { data[PARAM_SPECIALIZATION_ID] = it }
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<MedicalDirectoryProviderEntity>> {
        val mobileNumber = data?.get(PARAM_MOBILE) as String
        val providerTypeId = data?.get(PARAM_PROVIDER_TYPE_ID) as String
        val govId = data?.get(PARAM_GOV_ID) as String
        val areaId = data?.get(PARAM_AREA_ID) as String
        val providerName = data?.get(PARAM_PROVIDER_NAME) as String
        val specializationId = data?.get(PARAM_SPECIALIZATION_ID) as String
        return neqabtyRepository.getMedicalDirectoryProviders(mobileNumber, providerTypeId, govId, areaId, providerName, specializationId)
    }
}