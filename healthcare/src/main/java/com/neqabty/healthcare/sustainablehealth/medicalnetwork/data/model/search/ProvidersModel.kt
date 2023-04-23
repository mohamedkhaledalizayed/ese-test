package com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ProvidersModel(
    @SerializedName("address")
    val address: String,
    @SerializedName("price_details")
    val price: String?,
    @SerializedName("area")
    val area: Area?,
    @SerializedName("area_id")
    val areaId: Int,
    @SerializedName("degree")
    val degree: Degree?,
    @SerializedName("degree_id")
    val degreeId: Int,
    @SerializedName("email")
    val email: String?,
    @SerializedName("governorate")
    val governorate: Governorate,
    @SerializedName("governorate_id")
    val governorateId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("profession")
    val profession: Profession?,
    @SerializedName("profession_id")
    val professionId: Int,
    @SerializedName("service_provider_type")
    val serviceProviderType: ServiceProviderType?,
    @SerializedName("service_provider_type_id")
    val serviceProviderTypeId: Int
)