package com.neqabty.healthcare.commen.mypackages.packages.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.neqabty.healthcare.commen.packages.packageslist.data.model.DetailModel

@Keep
data class Package(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("subscriber_id")
    val subscriberId: String?,
    @SerializedName("deleted_at")
    val deletedAt: Any,
    @SerializedName("details")
    val details: List<DetailModel>,
    @SerializedName("description_ar")
    val descriptionAr: String?,
    @SerializedName("extension")
    val extension: String?,
    @SerializedName("description_en")
    val descriptionEn: Any,
    @SerializedName("followers")
    val followers: List<Follower>,
    @SerializedName("hint")
    val hint: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("insurance_amount")
    val insuranceAmount: Any,
    @SerializedName("insurance_docs")
    val insuranceDocs: List<String>,
    @SerializedName("insurance_company_id")
    val insuranceCompanyId: Any,
    @SerializedName("name")
    val name: String?,
    @SerializedName("name_ar")
    val nameAr: String?,
    @SerializedName("name_en")
    val nameEn: String?,
    @SerializedName("nedded_info")
    val neddedInfo: Any,
    @SerializedName("paid")
    val paid: Boolean,
    @SerializedName("recommended")
    val recommended: Boolean,
    @SerializedName("short_description")
    val shortDescription: String?,
    @SerializedName("prepaid")
    val prepaid: Boolean,
    @SerializedName("service_code")
    val serviceCode: String?,
    @SerializedName("service_action_code")
    val serviceActionCode: String?,
    @SerializedName("package_price")
    val packagePrice: String?,
    @SerializedName("vat")
    val vat: String?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("expiry_date")
    val expiryDate: String?,
    @SerializedName("max_follower")
    val maxFollower: Int,
    @SerializedName("target_groups")
    val targetGroups: Any,
    @SerializedName("updated_at")
    val updatedAt: String
)