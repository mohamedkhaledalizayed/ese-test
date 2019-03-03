package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.TripEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetTripDetails @Inject constructor(transformer: Transformer<TripEntity>,
                                         private val neqabtyRepository: NeqabtyRepository) : UseCase<TripEntity>(transformer) {


    companion object {
        private const val PARAM_TRIP_ID = "param:tripId"
    }

    fun getTripDetails(id: String): Observable<TripEntity> {
        val data = HashMap<String, String>()
        data[PARAM_TRIP_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<TripEntity> {
        val id = data?.get(GetTripDetails.PARAM_TRIP_ID) as String
        return neqabtyRepository.getTripDetails(id)
    }

}