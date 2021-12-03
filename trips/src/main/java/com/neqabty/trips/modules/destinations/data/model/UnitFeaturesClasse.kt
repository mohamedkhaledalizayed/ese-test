package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName

data class UnitFeaturesClasse(
    @SerializedName("categories")
    val categories: List<Category> = listOf(),
    @SerializedName("destination")
    val destination: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("links")
    val links: LinksX = LinksX(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("num_of_flats")
    val numOfFlats: Int = 0,
    @SerializedName("person_no")
    val personNo: Int = 0,
    @SerializedName("pricings")
    val pricings: List<Pricing> = listOf(),
    @SerializedName("rooms_no")
    val roomsNo: Int = 0
)