package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class TrackShipmentData(
    @field:SerializedName("name")
    var name: String,
    @field:SerializedName("address")
    var address: String,
    @field:SerializedName("user_number")
    var userNumber: String,
    @field:SerializedName("phone")
    var phone: String,
    @field:SerializedName("city")
    var city: String,
    @field:SerializedName("date")
    var date: String,
    @field:SerializedName("shipment_provider")
    var shipmentProvider: String,
    @field:SerializedName("status")
    var status: String
) : Response()