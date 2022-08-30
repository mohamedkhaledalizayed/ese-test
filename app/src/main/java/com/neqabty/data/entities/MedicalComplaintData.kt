package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MedicalComplaintData(
    @field:SerializedName("ResultType")
    var resultType: String = "",
    @field:SerializedName("Id")
    var requestID: String = "",
    @field:SerializedName("Message")
    var msg: String? = "",
    @field:SerializedName("IsSuccess")
    var isSuccess: Boolean = false
) : Response()