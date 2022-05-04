package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ServiceTypeEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllServiceTypes @Inject constructor(
    transformer: Transformer<ServiceTypeEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<ServiceTypeEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getAllServiceTypes(userNumber: String): Observable<ServiceTypeEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ServiceTypeEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.getAllServiceTypes(userNumber)
    }
}