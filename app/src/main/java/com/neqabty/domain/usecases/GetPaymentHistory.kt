package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.PaymentHistoryEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetPaymentHistory @Inject constructor(
    transformer: Transformer<List<PaymentHistoryEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<PaymentHistoryEntity>>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getPaymentHistory(
        userNumber: String
    ): Observable<List<PaymentHistoryEntity>> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<PaymentHistoryEntity>> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.getPaymentHistory(userNumber)
    }
}