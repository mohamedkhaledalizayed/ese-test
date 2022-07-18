package com.neqabty.healthcare.modules.search.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*
@Keep
data class MedicalProviderModel(
    @SerializedName("added_by")
    val addedBy: String? = null,
    @SerializedName("address")
    val address: String = "",
    @SerializedName("area")
    val area: Area? = Area(),
    @SerializedName("area_id")
    val areaId: Int = 0,
    @SerializedName("created_at")
    val createdAt: Date? = null ,
    @SerializedName("degree")
    val degree: Degree? = Degree(),
    @SerializedName("degree_id")
    val degreeId: Int = 0,
    @SerializedName("deleted_at")
    val deletedAt: Date? = null ,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("governorate")
    val governorate: Governorate? = Governorate(),
    @SerializedName("governorate_id")
    val governorateId: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("profession")
    val profession: Profession? = Profession(),
    @SerializedName("profession_id")
    val professionId: Int = 0,
    @SerializedName("service_provider_type")
    val serviceProviderType: ServiceProviderType? = ServiceProviderType(),
    @SerializedName("service_provider_type_id")
    val serviceProviderTypeId: Int = 0,
    @SerializedName("updated_at")
    val updatedAt: Date? = null
)