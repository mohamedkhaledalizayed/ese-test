package com.neqabty.healthcare.commen.clinido.data.datasource

import com.neqabty.healthcare.commen.clinido.data.api.ClinidoApi
import com.neqabty.healthcare.commen.clinido.data.model.ClinidoBody
import com.neqabty.healthcare.commen.clinido.data.model.ClinidoModel
import javax.inject.Inject

class ClinidoDS @Inject constructor(private val clinidoApi: ClinidoApi) {

    suspend fun register(clinidoBody: ClinidoBody): ClinidoModel{
        return clinidoApi.register(clinidoBody)
    }
}