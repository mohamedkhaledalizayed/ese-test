package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.RegisteryEntity
import io.reactivex.Observable
import javax.inject.Inject

class SendEngineeringRecordsInquiry @Inject constructor(
    transformer: Transformer<RegisteryEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<RegisteryEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
    }

    fun inquireEngineeringRecords(
        userNumber: String
    ): Observable<RegisteryEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<RegisteryEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.inquireEngineeringRecords(userNumber)
    }
}