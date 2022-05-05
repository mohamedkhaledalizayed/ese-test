package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class CommitteesRequest(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("user_number")
    var user_number: String = "",
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("national_id")
    var nationalId: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("university")
    var university: String = "",
    @SerializedName("degree")
    var degree: String = "",
    @SerializedName("marital_status")
    var maritalStatus: String = "",
    @SerializedName("committee_ids")
    var committeesIds: List<Int> = listOf(),
    @SerializedName("section_id")
    var sectionId: Int = 0,
    @SerializedName("syndicate_id")
    var syndicateId: Int = 0,
    @SerializedName("section")
    var section: String = "",
    @SerializedName("current_job")
    var currentJob: String = "",
    @SerializedName("details")
    var details: String = "",
    @SerializedName("docs_num")
    var docsNumber: Int = 0
) : Request()
