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
    @field:SerializedName("hotel_one_person")
    var price: String?,
    @field:SerializedName("images")
    var imgs: List<String>? = null
) : Response() {
        data class TripImage(
            @field:SerializedName("id")
            var imageId: Int = 0,
            @field:SerializedName("image")
            var file: String?,
            @field:SerializedName("place_id")
            var tripId: Int?,
            @field:SerializedName("created_by")
            var createdBy: String?,
            @field:SerializedName("updated_by")
            var updatedBy: String?,
            @field:SerializedName("created_at")
            var createdAt: String?,
            @field:SerializedName("updated_at")
            var updatedAt: String?
        )
}