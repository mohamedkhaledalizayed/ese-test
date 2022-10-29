package com.neqabty.courses.offers.domain.interactors

import com.neqabty.courses.offers.data.model.RescheduleRequestBody
import com.neqabty.courses.offers.domain.repository.OffersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RescheduleRequestUseCase @Inject constructor(private val offersRepository: OffersRepository) {
    fun build(rescheduleRequestBody: RescheduleRequestBody): Flow<String>{
        return offersRepository.rescheduleRequests(rescheduleRequestBody)
    }
}