package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class MedicalProviderData(
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("name")
    var name: String?,
    @field:SerializedName("governorate_id")
    var governId: String?,
    @field:SerializedName("area_id")
    var areaId: String?,
    @field:SerializedName("addresss")
    var address: String?,
    @field:SerializedName("phones")
    var phones: String?,
    @field:SerializedName("emails")
    var emails: String?,
    @field:SerializedName("created_by")
    var createdBy: String?,
    @field:SerializedName("updated_by")
    var updatedBy: String?,
    @field:SerializedName("created_at")
    var createdAt: String?,
    @field:SerializedName("updated_at")
    var updatedAt: String?,
    @field:SerializedName("service_provider_id")
    var serviceProviderId: String?
) : Response()