package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class MedicalRequest(
    @SerializedName("main_syndicate_id")
    var mainSyndicate: Int = 0,
    @SerializedName("sub_syndicate_id")
    var subSyndicate: Int = 0,
    @SerializedName("syndicate_user_number")
    var userNumber: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("profession_id")
    var professionId: Int = 0,
    @SerializedName("degree_id")
    var degreeId: Int = 0,
    @SerializedName("gov_id")
    var govId: Int = 0,
    @SerializedName("area_id")
    var areaId: Int = 0,
    @SerializedName("doctor_id")
    var doctorId: Int = 0,
    @SerializedName("provider_type_id")
    var providerTypeId: Int = 0,
    @SerializedName("provider_id")
    var providerId: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("oldbenid")
    var oldbenid: String = "",
    @SerializedName("request_details")
    var details: String = "",
    @SerializedName("follower_name")
    var followerName: String = "",
    @SerializedName("follower_relationship")
    var followerRelation: String = "",
    @SerializedName("docs_num")
    var docsNumber: Int = 0
) : Request()
