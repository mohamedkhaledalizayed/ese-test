package com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.filter


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class FiltersModel(
    @SerializedName("governorates")
    val governorates: List<Governorate>,
    @SerializedName("professions")
    val professions: List<Profession>,
    @SerializedName("providerTypes")
    val providerTypes: List<ProviderType>,
    @SerializedName("degrees")
    val degrees: List<Degree>
)