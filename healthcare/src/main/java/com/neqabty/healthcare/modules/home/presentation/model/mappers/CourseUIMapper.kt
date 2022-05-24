package com.neqabty.healthcare.modules.home.presentation.model.mappers

import com.neqabty.healthcare.modules.home.domain.entity.ProviderEntity
import com.neqabty.healthcare.modules.home.presentation.model.Provider

fun ProviderEntity.toCourseUIModel(): Provider {
    return Provider(this.name)
}