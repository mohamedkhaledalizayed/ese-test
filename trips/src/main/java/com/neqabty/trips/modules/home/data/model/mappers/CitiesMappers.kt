package com.neqabty.trips.modules.home.data.model.mappers

import com.neqabty.trips.modules.home.data.model.CityModel
import com.neqabty.trips.modules.home.domain.entity.CityEntity

fun CityModel.toCityEntity(): CityEntity {
    return CityEntity(
        this.id, this.image, this.name
    )
}