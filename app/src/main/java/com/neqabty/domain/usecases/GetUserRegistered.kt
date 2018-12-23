package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import io.reactivex.Observable
import javax.inject.Inject

class GetUserRegistered @Inject constructor(transformer: Transformer<Unit>,
                                            private val neqabtyRepository: NeqabtyRepository) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_MAIN_SYNDICATE = "param:mainSyndicate"
        private const val PARAM_SUB_SYNDICATE = "param:subSyndicate"
        private const val PARAM_TOKEN = "param:token"
    }

    fun getUserRegistered(mobile: String, mainSyndicateId: String, subSyndicateId: String, token : String): Observable<Unit> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobile
        data[PARAM_MAIN_SYNDICATE] = mainSyndicateId
        data[PARAM_SUB_SYNDICATE] = subSyndicateId
        data[PARAM_TOKEN] = token
        return observable(data)
    }
    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val mobile = data?.get(GetUserRegistered.PARAM_MOBILE) as String
        val mainSyndicateId = data?.get(GetUserRegistered.PARAM_MAIN_SYNDICATE) as String
        val subSyndicateId = data?.get(GetUserRegistered.PARAM_SUB_SYNDICATE) as String
        val token = data?.get(GetUserRegistered.PARAM_TOKEN) as String
        return neqabtyRepository.registerUser(mobile , mainSyndicateId,subSyndicateId,token)
    }

}