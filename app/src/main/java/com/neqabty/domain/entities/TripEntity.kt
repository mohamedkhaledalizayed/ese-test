package com.neqabty.domain.entities

data class TripEntity(
        var id: Int = 0,
        var img: String?,
        var title: String?,
        var typeId: String?,
        var dateFrom: String?,
        var dateTo: String?,
        var mainSyndicateId: String?,
        var subSyndicateId: String?,
        var governId: String?,
        var desc: String?,
        var price: String?,
        var imgs: List<String>? = null
){
    data class TripImage(
            var imageId: Int = 0,
            var file: String?,
            var tripId: Int?,
            var createdBy: String?,
            var updatedBy: String?,
            var createdAt: String?,
            var updatedAt: String?
    )
}