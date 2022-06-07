package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class MedicalBranchProcedureData(
    @field:SerializedName("ProfileId")
    var profileId: Int = 0,
    @field:SerializedName("ProfileName")
    var profileName: String?,
    @field:SerializedName("Address")
    var address: String?,
    @field:SerializedName("PhoneNumber")
    var phone: String?,
    @field:SerializedName("ServiceProviderId")
    var serviceProviderId: Int?,
    @field:SerializedName("MedicalProcedurePrice")
    var medicalProcedurePrice: Int?
) : Response()