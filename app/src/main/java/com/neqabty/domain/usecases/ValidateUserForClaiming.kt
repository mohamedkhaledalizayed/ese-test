package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ClaimingValidationEntity
import com.neqabty.domain.entities.MemberEntity
import io.reactivex.Observable
import javax.inject.Inject

class ValidateUserForClaiming @Inject constructor(
    transformer: Transformer<ClaimingValidationEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<ClaimingValidationEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun validateUser(userNumber: String): Observable<ClaimingValidationEntity> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ClaimingValidationEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.validateUserForClaiming(userNumber)
    }
}