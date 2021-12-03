package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.DoctorsReservationEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetDoctorsReservationData @Inject constructor(
        transformer: Transformer<DoctorsReservationEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<DoctorsReservationEntity>(transformer) {

    companion object {
        private const val PARAM_MOBILE_NUMBER = "param:mobile"
    }

    fun getData(mobileNumber: String): Observable<DoctorsReservationEntity> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<DoctorsReservationEntity> {
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        return neqabtyRepository.getDoctorsReservationData(mobileNumber)
    }
}