package com.neqabty.trips.modules.home.data.model.mappers

import com.neqabty.trips.modules.home.data.model.TripsModel
import com.neqabty.trips.modules.home.domain.entity.TripsEntity

fun TripsModel.toCourseEntity(): TripsEntity {
    return TripsEntity(this.courseName)
}