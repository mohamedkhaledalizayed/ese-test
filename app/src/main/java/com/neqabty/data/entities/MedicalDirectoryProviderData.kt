package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class MedicalDirectoryProviderData(
    @field:SerializedName("Id")
    var id: String = "",
    @field:SerializedName("ProfileName")
    var name: String? = "",
    @field:SerializedName("Address")
    var address: String? = "",
    @field:SerializedName("PhoneNumber")
    var phone: String? = "",
    @field:SerializedName("Email")
    var email: String? = ""
) : Response()