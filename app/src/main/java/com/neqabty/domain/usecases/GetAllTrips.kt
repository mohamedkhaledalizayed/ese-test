package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.TripEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllTrips @Inject constructor(transformer: Transformer<List<TripEntity>>,
                                      private val neqabtyRepository: NeqabtyRepository) : UseCase<List<TripEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<TripEntity>> {
        return neqabtyRepository.getTrips()
    }

}