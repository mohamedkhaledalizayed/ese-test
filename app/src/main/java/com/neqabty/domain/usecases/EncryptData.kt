package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AppVersionEntity
import com.neqabty.domain.entities.EncryptionEntity
import io.reactivex.Observable
import javax.inject.Inject

class EncryptData @Inject constructor(
        transformer: Transformer<EncryptionEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<EncryptionEntity>(transformer) {

    companion object {
        private const val PARAM_USERNAME = "param:userName"
        private const val PARAM_PASSWORD = "param:password"
        private const val PARAM_DESCRIPTION = "param:description"
    }


    fun encryptData(username: String, password: String, description: String): Observable<EncryptionEntity> {
        val data = HashMap<String, String>()
        data[PARAM_USERNAME] = username
        data[PARAM_PASSWORD] = password
        data[PARAM_DESCRIPTION] = description
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<EncryptionEntity> {
        val username = data?.get(PARAM_USERNAME) as String
        val password = data?.get(PARAM_PASSWORD) as String
        val description = data?.get(PARAM_DESCRIPTION) as String
        return neqabtyRepository.encrypt(username,password,description)
    }
}