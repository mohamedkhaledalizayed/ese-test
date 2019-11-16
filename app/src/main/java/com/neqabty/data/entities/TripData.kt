package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class TripData(
    @field:SerializedName("trip_ID")
    var id: Int = 0,
    @field:SerializedName("trip_image")
    var img: String?,
    @field:SerializedName("trip_title")
    var title: String?,
    @field:SerializedName("type_id")
    var typeId: String?,
    @field:SerializedName("trip_dateFrom")
    var dateFrom: String?,
    @field:SerializedName("trip_dateTo")
    var dateTo: String?,
    @field:SerializedName("mainSyndicate_id")
    var mainSyndicateId: String?,
    @field:SerializedName("subSyndicate_id")
    var subSyndicateId: String?,
    @field:SerializedName("governorate_id")
    var governId: String?,
    @field:SerializedName("trip_desc")
    var desc: String?,
    @field:SerializedName("notes")
    var notes: String?,
    @field:SerializedName("price")
    var price: String?,
    @field:SerializedName("images")
    var imgs: List<String>? = null,
    @field:SerializedName("regiments")
    var regiments: List<TripRegiment>? = null,
    @field:SerializedName("place")
    var place: TripPlace? = null
) : Response() {
    data class TripRegiment(
        @field:SerializedName("id")
        var regimentId: Int = 0,
        @field:SerializedName("trip_id")
        var tripId: Int?,
        @field:SerializedName("date_from")
        var dateFrom: String?,
        @field:SerializedName("date_to")
        var dateTo: String?,
        @field:SerializedName("hotel_one_person")
        var hotelOnePerson: Int?,
        @field:SerializedName("hotel_tow_person")
        var hotelTwoPerson: Int?,
        @field:SerializedName("hotel_three_person")
        var hotelThreePerson: Int?,
        @field:SerializedName("apartment_view_price")
        var viewPrice: Int?,
        @field:SerializedName("apartment_side_price")
        var sidePrice: Int?,
        @field:SerializedName("price")
        var price: Int?,
        @field:SerializedName("apartment_oneRoom_price")
        var oneRoom: Int?,
        @field:SerializedName("apartment_towRooms_price")
        var twoRooms: Int?,
        @field:SerializedName("apartment_studio_price")
        var studio: Int?,
        @field:SerializedName("trip_type")
        var tripType: String?,
        @field:SerializedName("created_by")
        var createdBy: String?,
        @field:SerializedName("updated_by")
        var updatedBy: String?,
        @field:SerializedName("created_at")
        var createdAt: String?,
        @field:SerializedName("updated_at")
        var updatedAt: String?
    )
    data class TripPlace(
        @field:SerializedName("id")
        var placeId: Int = 0,
        @field:SerializedName("details")
        var details: String?,
        @field:SerializedName("name")
        var name: String?
    )
}