package com.neqabty.presentation.mappers

import com.neqabty.data.entities.HealthCareProjectStatusData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.HealthCareProjectStatusEntity
import com.neqabty.presentation.entities.HealthCareProjectStatusUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthCareProjectStatusEntityUIMapper @Inject constructor() : Mapper<HealthCareProjectStatusEntity, HealthCareProjectStatusUI>() {

    override fun mapFrom(from: HealthCareProjectStatusEntity): HealthCareProjectStatusUI {
        return HealthCareProjectStatusUI(
                status = from.status,
                statusMsg = from.statusMsg
        )
    }
}
