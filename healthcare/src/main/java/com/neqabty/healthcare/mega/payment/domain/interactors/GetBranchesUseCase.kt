package com.neqabty.healthcare.mega.payment.domain.interactors


import com.neqabty.healthcare.mega.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBranchesUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun getBranches(): Flow<List<BranchesEntity>> {
        return repository.getBranches()
    }

}