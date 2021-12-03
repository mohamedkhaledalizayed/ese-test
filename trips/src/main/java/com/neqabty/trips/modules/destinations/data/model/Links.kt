package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("features")
    val features: String = "",
    @SerializedName("unit_features_classes")
    val unitFeaturesClasses: String = ""
)