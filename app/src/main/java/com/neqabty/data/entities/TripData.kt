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
        var price: String?
): Response()