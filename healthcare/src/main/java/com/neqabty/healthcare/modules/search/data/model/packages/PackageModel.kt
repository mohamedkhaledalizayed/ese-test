package com.neqabty.healthcare.modules.search.data.model.packages


import com.google.gson.annotations.SerializedName

data class PackageModel(
    @SerializedName("description")
    val description: String,
    @SerializedName("details")
    val details: List<DetailModel>,
    @SerializedName("follower_multi_relation")
    val followerMultiRelation: Boolean,
    @SerializedName("has_follower")
    val hasFollower: Boolean,
    @SerializedName("hint")
    val hint: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("insurance_amount")
    val insuranceAmount: String?,
    @SerializedName("max_follower")
    val maxFollower: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("nedded_info")
    val neddedInfo: String?,
    @SerializedName("price")
    val price: Double,
    @SerializedName("recommended")
    val recommended: Boolean,
    @SerializedName("service_action_code")
    val serviceActionCode: String,
    @SerializedName("service_code")
    val serviceCode: String,
    @SerializedName("short_description")
    val shortDescription: String?,
    @SerializedName("target_groups")
    val targetGroups: String?
)