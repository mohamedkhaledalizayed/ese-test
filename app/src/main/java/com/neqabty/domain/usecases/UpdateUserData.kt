package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.InquireUpdateUserDataEntity
import com.neqabty.domain.entities.UpdateUserDataEntity
import io.reactivex.Observable
import javax.inject.Inject

class UpdateUserData @Inject constructor(
        transformer: Transformer<UpdateUserDataEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<UpdateUserDataEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_FULL_NAME = "param:fullName"
        private const val PARAM_NATIONAL_ID = "param:nationalID"
        private const val PARAM_GENDER = "param:gender"
        private const val PARAM_USER_ID = "param:userID"
    }

    fun updateUserData(
            userNumber: String,fullName: String,nationalID: String,gender: String,userID: String
    ): Observable<UpdateUserDataEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_FULL_NAME] = fullName
        data[PARAM_NATIONAL_ID] = nationalID
        data[PARAM_GENDER] = gender
        data[PARAM_USER_ID] = userID
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<UpdateUserDataEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val fullName = data?.get(PARAM_FULL_NAME) as String
        val nationalID = data?.get(PARAM_NATIONAL_ID) as String
        val gender = data?.get(PARAM_GENDER) as String
        val userID = data?.get(PARAM_USER_ID) as String
        return neqabtyRepository.updateUserData(userNumber, fullName, nationalID, gender, userID)
    }
}