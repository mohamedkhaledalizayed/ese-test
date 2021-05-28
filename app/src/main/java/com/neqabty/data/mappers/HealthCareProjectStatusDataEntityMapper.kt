package com.neqabty.data.mappers

import com.neqabty.data.entities.HealthCareProjectStatusData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.HealthCareProjectStatusEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthCareProjectStatusDataEntityMapper @Inject constructor() : Mapper<HealthCareProjectStatusData, HealthCareProjectStatusEntity>() {

    override fun mapFrom(from: HealthCareProjectStatusData): HealthCareProjectStatusEntity {
        return HealthCareProjectStatusEntity(
                status = from.status,
                statusMsg = from.statusMsg ?: ""
        )
    }
}
