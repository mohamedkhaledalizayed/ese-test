package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MemberEntity
import io.reactivex.Observable
import javax.inject.Inject

class ValidateUser @Inject constructor(
    transformer: Transformer<MemberEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<MemberEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun validateUser(userNumber: String): Observable<MemberEntity> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MemberEntity> {
        val userNumber = data?.get(ValidateUser.PARAM_USER_NUMBER) as String
        return neqabtyRepository.validateUser(userNumber.toInt())
    }
}