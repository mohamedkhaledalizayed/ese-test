package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName
import com.neqabty.trips.modules.home.data.model.CityModel

data class DestinationModel(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("city")
    val city: CityModel,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("features")
    val features: List<Feature> = listOf(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("links")
    val links: Links = Links(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("notes")
    val notes: List<String> = listOf(),
    @SerializedName("unit_features_classes")
    val unitFeaturesClasses: List<UnitFeaturesClasse> = listOf()
)