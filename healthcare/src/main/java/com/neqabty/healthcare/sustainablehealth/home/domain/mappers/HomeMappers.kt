package com.neqabty.healthcare.sustainablehealth.home.domain.mappers

import com.neqabty.healthcare.sustainablehealth.home.data.model.about.AboutModel
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity


fun AboutModel.toAboutEntity(): AboutEntity{
    return AboutEntity(
        id = id,
        key = key,
        value = value
    )
}