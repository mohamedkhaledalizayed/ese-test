package com.neqabty.healthcare.clinido.data.datasource

import com.neqabty.healthcare.clinido.data.api.ClinidoApi
import com.neqabty.healthcare.clinido.data.model.ClinidoBody
import com.neqabty.healthcare.clinido.data.model.ClinidoModel
import javax.inject.Inject

class ClinidoDS @Inject constructor(private val clinidoApi: ClinidoApi) {

    suspend fun register(clinidoBody: ClinidoBody): ClinidoModel{
        return clinidoApi.register(clinidoBody)
    }
}