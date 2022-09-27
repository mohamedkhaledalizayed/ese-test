package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class ProfileData(
    @field:SerializedName("club_invitations")
    var invitations: Int = 0,
    @field:SerializedName("qr_code")
    var code: String?,
    @field:SerializedName("name")
    var name: String?,
    @field:SerializedName("governerate")
    var governerate: String?,
    @field:SerializedName("syndicate")
    var syndicate: String?,
    @field:SerializedName("section")
    var section: String?,
    @field:SerializedName("image")
    var image: String?
) : Response()