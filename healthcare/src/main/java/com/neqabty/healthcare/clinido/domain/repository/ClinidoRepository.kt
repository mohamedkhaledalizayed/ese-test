package com.neqabty.healthcare.clinido.domain.repository


import com.neqabty.healthcare.clinido.data.model.ClinidoBody
import com.neqabty.healthcare.clinido.domain.entity.ClinidoEntity
import kotlinx.coroutines.flow.Flow

interface ClinidoRepository {
    fun register(clinidoBody: ClinidoBody): Flow<ClinidoEntity>
}