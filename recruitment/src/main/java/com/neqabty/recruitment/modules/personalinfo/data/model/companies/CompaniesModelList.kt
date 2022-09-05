package com.neqabty.recruitment.modules.personalinfo.data.model.companies


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CompaniesModelList(
    @SerializedName("companies")
    val companies: List<CompanyModel>
)