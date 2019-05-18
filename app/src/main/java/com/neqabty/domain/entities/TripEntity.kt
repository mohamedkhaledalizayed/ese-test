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
    var notes: String?,
    var imgs: List<String>? = null,
    var regiments: List<TripRegiment>? = null,
    var place: TripPlace? = null
) {
    data class TripRegiment(
        var regimentId: Int = 0,
        var tripId: Int?,
        var dateFrom: String?,
        var dateTo: String?,
        var hotelOnePerson: Int?,
        var hotelTwoPerson: Int?,
        var hotelThreePerson: Int?,
        var viewPrice: Int?,
        var sidePrice: Int?,
        var price: Int?,
        var tripType: String?,
        var createdBy: String?,
        var updatedBy: String?,
        var createdAt: String?,
        var updatedAt: String?
    )
    data class TripPlace(
        var placeId: Int = 0,
        var details: String?
    )
}