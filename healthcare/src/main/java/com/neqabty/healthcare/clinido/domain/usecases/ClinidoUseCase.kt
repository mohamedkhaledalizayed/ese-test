package com.neqabty.healthcare.clinido.domain.usecases



import com.neqabty.healthcare.clinido.data.model.ClinidoBody
import com.neqabty.healthcare.clinido.domain.entity.ClinidoEntity
import com.neqabty.healthcare.clinido.domain.repository.ClinidoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClinidoUseCase @Inject constructor(private val clinidoRepository: ClinidoRepository) {
    fun build(phone: String, type: String, name: String, entityCode: String): Flow<ClinidoEntity> {
        return clinidoRepository.register(ClinidoBody(mobile = phone, type = type, name = name, entityCode = entityCode))
    }
}