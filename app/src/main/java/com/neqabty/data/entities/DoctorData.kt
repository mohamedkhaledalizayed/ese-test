package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class DoctorData(
        @field:SerializedName("doctor_id")
        var id: Int = 0,
        @field:SerializedName("serial")
        var serial: String?,
        @field:SerializedName("category")
        var category: String?,
        @field:SerializedName("Profession_Code")
        var profissionCode: String?,
        @field:SerializedName("name")
        var name: String?,
        @field:SerializedName("degree")
        var degree: String?,
        @field:SerializedName("Degree_Code")
        var degreeCode: String?,
        @field:SerializedName("address")
        var address: String?,
        @field:SerializedName("area")
        var area: String?,
        @field:SerializedName("Area Code")
        var areaCode: String?,
        @field:SerializedName("phone_code")
        var phoneCode: String?,
        @field:SerializedName("phone_num")
        var phoneNumber: String?
): Response()