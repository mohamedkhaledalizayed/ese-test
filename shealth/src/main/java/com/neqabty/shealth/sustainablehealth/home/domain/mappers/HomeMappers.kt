package com.neqabty.shealth.sustainablehealth.home.domain.mappers

import com.neqabty.shealth.sustainablehealth.home.data.model.about.AboutModel
import com.neqabty.shealth.sustainablehealth.home.domain.entity.about.AboutEntity


fun AboutModel.toAboutEntity(): AboutEntity{
    return AboutEntity(
        id = id,
        key = key,
        value = value
    )
}