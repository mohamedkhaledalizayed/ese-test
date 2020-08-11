package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.DecryptionEntity
import io.reactivex.Observable
import javax.inject.Inject

class SendDecryptionKey @Inject constructor(
    transformer: Transformer<DecryptionEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<DecryptionEntity>(transformer) {

    companion object {
        private const val PARAM_REQUEST_NUMBER = "param:requestNumber"
        private const val PARAM_KEY = "param:key"
    }

    fun sendDecryptionKey(requestNumber: String, decryptionKey: String): Observable<DecryptionEntity> {
        val data = HashMap<String, String>()
        data[PARAM_REQUEST_NUMBER] = requestNumber
        data[PARAM_KEY] = decryptionKey
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<DecryptionEntity> {
        val requestNumber = data?.get(PARAM_REQUEST_NUMBER) as String
        val decryptionKey = data.get(PARAM_KEY) as String
        return neqabtyRepository.sendDecryptionKey(requestNumber, decryptionKey)
    }
}