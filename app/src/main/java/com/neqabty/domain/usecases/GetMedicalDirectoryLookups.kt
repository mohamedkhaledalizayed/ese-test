package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalDirectoryLookupsEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalDirectoryLookups @Inject constructor(
    transformer: Transformer<MedicalDirectoryLookupsEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalDirectoryLookupsEntity>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
    }

    fun getLookups(
        mobileNumber: String
    ): Observable<MedicalDirectoryLookupsEntity> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalDirectoryLookupsEntity> {
        val mobileNumber = data?.get(PARAM_MOBILE) as String
        return neqabtyRepository.getMedicalDirectoryLookups(mobileNumber)
    }
}