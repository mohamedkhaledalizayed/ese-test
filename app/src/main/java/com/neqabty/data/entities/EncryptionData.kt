package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["encryption"])
data class EncryptionData(
    @field:SerializedName("data")
    var encryption: String
) : Response()