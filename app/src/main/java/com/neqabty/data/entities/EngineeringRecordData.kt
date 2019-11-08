package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["registryDataID"])
data class EngineeringRecordData(
        @field:SerializedName("status_code")
        var statusCode: Int = 0,
        @field:SerializedName("registryData")
        var registeryData: RegisteryData
) : Response()

data class RegisteryData(
        @field:SerializedName("status_code")
        var statusCode: Int = 0,
        @field:SerializedName("RegistryDataID")
        var registryDataID: String? = "",
        @field:SerializedName("RegistryEngineerID")
        var registryEngineerID: String?,
        @field:SerializedName("EngID")
        var engID: String?,
        @field:SerializedName("ContactID")
        var contactID: String?,
        @field:SerializedName("FullName")
        var fullName: String?,
        @field:SerializedName("LastRenewYear")
        var lastRenewYear: String?,
        @field:SerializedName("RegistryTypeID")
        var registryTypeID: String?,
        @field:SerializedName("RegDataStatusID")
        var regDataStatusID: String?,
        @field:SerializedName("IsOwner")
        var isOwner: Int?,
        @field:SerializedName("BirthDate")
        var birthDate: String?,
        @field:SerializedName("Mobile")
        var mobile: String?,
        @field:SerializedName("RegisterOffice")
        var registerOffice: String?
)