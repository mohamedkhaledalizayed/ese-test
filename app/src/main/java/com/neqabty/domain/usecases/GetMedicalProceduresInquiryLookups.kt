package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalProceduresInquiryLookupsEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetMedicalProceduresInquiryLookups @Inject constructor(
    transformer: Transformer<MedicalProceduresInquiryLookupsEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalProceduresInquiryLookupsEntity>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
    }

    fun getLookups(
        mobileNumber: String
    ): Observable<MedicalProceduresInquiryLookupsEntity> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalProceduresInquiryLookupsEntity> {
        val mobileNumber = data?.get(PARAM_MOBILE) as String
        return neqabtyRepository.getMedicalProceduresInquiryLookups(mobileNumber)
    }
}