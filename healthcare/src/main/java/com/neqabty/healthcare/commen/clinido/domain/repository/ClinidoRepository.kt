package com.neqabty.healthcare.commen.clinido.domain.repository


import com.neqabty.healthcare.commen.clinido.data.model.ClinidoBody
import com.neqabty.healthcare.commen.clinido.domain.entity.ClinidoEntity
import kotlinx.coroutines.flow.Flow

interface ClinidoRepository {
    fun register(clinidoBody: ClinidoBody): Flow<ClinidoEntity>
}