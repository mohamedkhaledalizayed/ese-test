package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalProviderEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalProviders @Inject constructor(transformer: Transformer<List<MedicalProviderEntity>>,
                                              private val neqabtyRepository: NeqabtyRepository) : UseCase<List<MedicalProviderEntity>>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
    }

    fun getMedicalProviders(id: String): Observable<List<MedicalProviderEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_ID] = id
        return observable(data)
    }
    override fun createObservable(data: Map<String, Any>?): Observable<List<MedicalProviderEntity>> {
        val id = data?.get(GetMedicalProviders.PARAM_ID) as String
        return neqabtyRepository.getMedicalProviders(id)
    }

}