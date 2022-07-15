package com.neqabty.healthcare.modules.profile.data.model.profile


import com.google.gson.annotations.SerializedName

data class SubscribedPackage(
    @SerializedName("action_by")
    val actionBy: Any,
    @SerializedName("address")
    val address: String,
    @SerializedName("back_id_image")
    val backIdImage: String,
    @SerializedName("birth_date")
    val birthDate: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("downloaded")
    val downloaded: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("expiry_date")
    val expiryDate: Any,
    @SerializedName("front_id_image")
    val frontIdImage: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("insurance_company_id")
    val insuranceCompanyId: Any,
    @SerializedName("insurance_doc")
    val insuranceDoc: Any,
    @SerializedName("insurance_status")
    val insuranceStatus: Boolean,
    @SerializedName("job")
    val job: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("notes")
    val notes: Any,
    @SerializedName("package_name")
    val packageName: String,
    @SerializedName("package")
    val packageX: Package,
    @SerializedName("personal_image")
    val personalImage: String,
    @SerializedName("referral_number")
    val referralNumber: Any,
    @SerializedName("status")
    val status: Int,
    @SerializedName("user_number")
    val userNumber: Any
)