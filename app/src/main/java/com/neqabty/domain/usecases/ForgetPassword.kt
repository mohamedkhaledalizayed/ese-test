package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class ForgetPassword @Inject constructor(
        transformer: Transformer<String>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<String>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
    }

    fun forgetPassword(mobile: String): Observable<String> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobile
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<String> {
        val mobile = data?.get(PARAM_MOBILE) as String
        return neqabtyRepository.forgetPassword(mobile)
    }
}