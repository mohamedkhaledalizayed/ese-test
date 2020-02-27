package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.SyndicateEntity
import io.reactivex.Observable
import javax.inject.Inject

class CreateComplaint @Inject constructor(
    transformer: Transformer<Unit>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_NAME = "param:name"
        private const val PARAM_MOBILE = "param:phone"
        private const val PARAM_TYPE = "param:type"
        private const val PARAM_DETAILS = "param:details"
        private const val PARAM_TOKEN = "param:token"
    }

    fun createComplaint(name: String, phone: String, type: String, body: String, token: String): Observable<Unit> {
        val data = HashMap<String, String>()
        data[PARAM_NAME] = name
        data[PARAM_MOBILE] = phone
        data[PARAM_TYPE] = type
        data[PARAM_DETAILS] = body
        data[PARAM_TOKEN] = token
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val name = data?.get(PARAM_NAME) as String
        val phone = data?.get(PARAM_MOBILE) as String
        val type = data?.get(PARAM_TYPE) as String
        val details = data?.get(PARAM_DETAILS) as String
        val token = data?.get(PARAM_TOKEN) as String
        return neqabtyRepository.createComplaint(name,phone,type,details,token)
    }
}