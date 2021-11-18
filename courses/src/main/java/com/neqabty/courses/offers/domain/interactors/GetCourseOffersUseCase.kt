package com.neqabty.courses.offers.domain.interactors

import com.neqabty.courses.offers.domain.entity.OfferEntity
import com.neqabty.courses.offers.domain.repository.OffersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCourseOffersUseCase @Inject constructor(private val offersRepository: OffersRepository) {
    fun build(courseId:Int): Flow<List<OfferEntity>>{
        return offersRepository.getCourseOffers(courseId)
    }
}