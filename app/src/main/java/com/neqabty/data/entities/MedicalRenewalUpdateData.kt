package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MedicalRenewalUpdateData(
    @field:SerializedName("ResultType")
    var requestID: String = ""
) : Response()