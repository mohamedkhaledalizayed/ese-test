package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.InquireUpdateUserDataEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetUpdateUserDataInquiry @Inject constructor(
        transformer: Transformer<InquireUpdateUserDataEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<InquireUpdateUserDataEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
    }

    fun inquireUpdateUserData(
        userNumber: String
    ): Observable<InquireUpdateUserDataEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<InquireUpdateUserDataEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.updateUserDataInquiry(userNumber)
    }
}