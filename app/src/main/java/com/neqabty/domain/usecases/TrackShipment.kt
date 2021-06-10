package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.TrackShipmentEntity
import io.reactivex.Observable
import javax.inject.Inject

class TrackShipment @Inject constructor(
        transformer: Transformer<List<TrackShipmentEntity>>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<TrackShipmentEntity>>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun trackShipment(userNumber: String): Observable<List<TrackShipmentEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<TrackShipmentEntity>> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.trackShipment(userNumber)
    }
}