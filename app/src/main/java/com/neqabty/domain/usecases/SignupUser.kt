package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class SignupUser @Inject constructor(
    transformer: Transformer<UserEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<UserEntity>(transformer) {

    companion object {
        private const val PARAM_EMAIL = "param:email"
        private const val PARAM_FNAME = "param:fName"
        private const val PARAM_LNAME = "param:lName"
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_GOVID = "param:govId"
        private const val PARAM_MAINSYNDICATEID = "param:mainSyndicateId"
        private const val PARAM_SUBSYNDICATEID = "param:subSyndicateId"
        private const val PARAM_PASSWORD = "param:password"
    }

    fun signup(
        email: String,
        fName: String,
        lName: String,
        mobile: String,
        govId: String,
        mainSyndicateId: String,
        subSyndicateId: String,
        password: String
    ): Observable<UserEntity> {
        val data = HashMap<String, String>()
        data[PARAM_EMAIL] = email
        data[PARAM_FNAME] = fName
        data[PARAM_LNAME] = lName
        data[PARAM_MOBILE] = mobile
        data[PARAM_GOVID] = govId
        data[PARAM_MAINSYNDICATEID] = mainSyndicateId
        data[PARAM_SUBSYNDICATEID] = subSyndicateId
        data[PARAM_PASSWORD] = password
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<UserEntity> {
        val email = data?.get(PARAM_EMAIL) as String
        val fName = data?.get(PARAM_FNAME) as String
        val lName = data?.get(PARAM_LNAME) as String
        val mobile = data?.get(PARAM_MOBILE) as String
        val govId = data?.get(PARAM_GOVID) as String
        val mainSyndicateId = data?.get(PARAM_MAINSYNDICATEID) as String
        val subSyndicateId = data?.get(PARAM_SUBSYNDICATEID) as String
        val password = data?.get(PARAM_PASSWORD) as String
        return neqabtyRepository.signup(email, fName, lName, mobile, govId, mainSyndicateId, subSyndicateId, password)
    }
}