package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class MedicalProcedureData(
    @field:SerializedName("Id")
    var id: Int = 0,
    @field:SerializedName("MedicalProcedureName")
    var name: String?,
    @field:SerializedName("CategoryId")
    var categoryId: Int
) : Response()