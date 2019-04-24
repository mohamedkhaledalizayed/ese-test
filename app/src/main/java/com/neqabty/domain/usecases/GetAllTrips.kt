package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.TripEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllTrips @Inject constructor(
    transformer: Transformer<List<TripEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<TripEntity>>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
    }

    fun getAllTrips(id: String): Observable<List<TripEntity>> {
        val data = HashMap<String, String>()
        data[GetAllTrips.PARAM_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<TripEntity>> {
        val id = data?.get(GetAllTrips.PARAM_ID) as String
        return neqabtyRepository.getTrips(id)
    }
}