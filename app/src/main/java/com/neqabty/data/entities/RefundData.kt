package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName

data class RefundData(
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("name")
    var name: String,
    @field:SerializedName("mobile")
    var mobile: String,
    @field:SerializedName("user_number")
    var userNumber: String,
    @field:SerializedName("benId")
    var benId: String,
    @field:SerializedName("description")
    var description: String,
    @field:SerializedName("branchProfileId")
    var branchProfileId: String,
    @field:SerializedName("serviceProviderId")
    var serviceProviderId: String,
    @field:SerializedName("letterTypeId")
    var letterTypeId: String,
    @SerializedName("mobile_token")
    var mobileToken: String,
    @field:SerializedName("status")
    var status: String,
    @field:SerializedName("syndicate_request_id")
    var syndicate_request_id: String,
    @field:SerializedName("created_at")
    var created_at: String
)