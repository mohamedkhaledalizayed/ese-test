package com.neqabty.healthcare.mega.home.domain.interactors



import com.neqabty.healthcare.mega.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {
    fun addComplain(mobile: String, email: String, message: String): Flow<String> {
        return homeRepository.addComplain(mobile, email, message)
    }
}