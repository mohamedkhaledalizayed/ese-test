package com.neqabty.healthcare.modules.home.data.model.mappers

import com.neqabty.healthcare.modules.home.data.model.HealthCareProvider
import com.neqabty.healthcare.modules.home.domain.entity.ProviderEntity

fun HealthCareProvider.toCourseEntity(): ProviderEntity {
    return ProviderEntity(this.name)
}