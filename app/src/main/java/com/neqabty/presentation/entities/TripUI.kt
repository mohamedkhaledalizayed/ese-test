package com.neqabty.presentation.entities

data class TripUI(
        var id: Int = 0,
        var img: String?,
        var title: String?,
        var typeId: String?,
        var dateFrom: String?,
        var dateTo: String?,
        var mainSyndicateId: String?,
        var subSyndicateId: String?,
        var governId: String?,
        var desc: String?
)