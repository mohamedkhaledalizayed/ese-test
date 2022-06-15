package com.neqabty.healthcare.modules.home.domain.mappers

import com.neqabty.healthcare.modules.home.data.model.about.AboutModel
import com.neqabty.healthcare.modules.home.domain.entity.about.AboutEntity


fun AboutModel.toAboutEntity(): AboutEntity{
    return AboutEntity(
        id = id,
        key = key,
        value = value
    )
}