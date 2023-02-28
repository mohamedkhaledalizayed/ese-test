package com.neqabty.healthcare.commen.clinido.data.repository



import com.neqabty.healthcare.commen.clinido.data.datasource.ClinidoDS
import com.neqabty.healthcare.commen.clinido.data.model.ClinidoBody
import com.neqabty.healthcare.commen.clinido.data.model.ClinidoModel
import com.neqabty.healthcare.commen.clinido.domain.entity.ClinidoEntity
import com.neqabty.healthcare.commen.clinido.domain.repository.ClinidoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClinidoRepositoryImpl @Inject constructor(private val clinidoDS: ClinidoDS): ClinidoRepository {

    override fun register(clinidoBody: ClinidoBody): Flow<ClinidoEntity> {
        return flow {
            emit(clinidoDS.register(clinidoBody).toClinidoEntity())
        }
    }

}

fun ClinidoModel.toClinidoEntity(): ClinidoEntity {
    return ClinidoEntity(
        url = data?.url ?: "",
        message = message,
        status = status,
        status_code = status_code
    )
}

