package com.neqabty.trips.modules.home.presentation.model.mappers

import com.neqabty.trips.modules.home.domain.entity.TripsEntity
import com.neqabty.trips.modules.home.presentation.model.TripsUIModel

fun TripsEntity.toCourseUIModel(): TripsUIModel {
    return TripsUIModel(this.courseName)
}