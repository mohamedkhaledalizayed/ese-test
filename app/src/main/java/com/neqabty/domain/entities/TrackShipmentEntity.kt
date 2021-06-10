package com.neqabty.domain.entities

data class TrackShipmentEntity(
    var name: String,
    var address: String,
    var userNumber: String,
    var phone: String,
    var city: String,
    var date: String,
    var shipmentProvider: String,
    var status: String
)