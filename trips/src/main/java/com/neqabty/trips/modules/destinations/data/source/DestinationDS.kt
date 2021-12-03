package com.neqabty.trips.modules.destinations.data.source

import com.neqabty.trips.modules.destinations.data.api.DestinationApi
import com.neqabty.trips.modules.destinations.data.model.DestinationModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DestinationDS @Inject constructor(private val destinationApi: DestinationApi) {
    fun getDestinations(): Flow<List<DestinationModel>> {
        return flow {
            emit(destinationApi.getDestinations().destinations)
        }
    }
}