package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.OnlinePharmacyEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetOnlinePharmacyURL @Inject constructor(
        transformer: Transformer<OnlinePharmacyEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<OnlinePharmacyEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getURL(userNumber: String): Observable<OnlinePharmacyEntity> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<OnlinePharmacyEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.getOnlinePharmacyURL(userNumber)
    }
}