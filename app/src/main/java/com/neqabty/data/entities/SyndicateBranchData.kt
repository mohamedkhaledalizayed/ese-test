package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class SyndicateBranchData(
    @field:SerializedName("SyndicateID")
    var id: Int = 0,
    @field:SerializedName("SyndicateName")
    var name: String?,
    @field:SerializedName("Location")
    var location: String?,
    @field:SerializedName("Address")
    var address: String?,
    @field:SerializedName("TelephoneNumber")
    var phone: String?
) : Response()