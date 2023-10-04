package com.neqabty.healthcare.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckMemberResponse(
    @SerializedName("authorized")
    val authorized: Boolean,
    @SerializedName("ocr_status")
    val ocrStatus: String?,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("ocrs")
    val ocrs: List<Ocr>,
    @SerializedName("client_data")
    val clientInfo: ClientInfoResponse?
)

@Keep
data class Ocr(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("ocr_uuid")
    val ocr_uuid: String,
    @SerializedName("image_type")
    val image_type: String,
    @SerializedName("result")
    val result: Result,
    @SerializedName("account")
    val account: Int
)

@Keep
data class Result(
    @SerializedName("extractedInfo")
    val extractedInfo: ExtractedInfo,
    @SerializedName("completed")
    val completed: Boolean
)

@Keep
data class ExtractedInfo(
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("religion")
    val religion: String?,
    @SerializedName("correctDoc")
    val correctDoc: String,
    @SerializedName("expiry_date")
    val expiry_date: String?,
    @SerializedName("national_id")
    val national_id: String?,
    @SerializedName("spouse_name")
    val spouse_name: String?,
    @SerializedName("profession_1")
    val profession_1: String?,
    @SerializedName("profession_2")
    val profession_2: String?,
    @SerializedName("release_date")
    val release_date: String?,
    @SerializedName("marital_status")
    val marital_status: String?,
    @SerializedName("area")
    val area: String?,
    @SerializedName("street")
    val street: String?,
    @SerializedName("full_name")
    val full_name: String?,
    @SerializedName("birth_date")
    val birth_date: String?,
    @SerializedName("first_name")
    val first_name: String?,
    @SerializedName("governorate")
    val governorate: String?,
    @SerializedName("serial_number")
    val serial_number: String?,
    @SerializedName("serial_validation")
    val serial_validation: String?
)

@Keep
data class ClientInfoResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("limit")
    val limit: Double,
    @SerializedName("available_balance")
    val availableBalance: Double,
    @SerializedName("next_due_date")
    val nextDueDate: String,
    @SerializedName("next_due_amount")
    val nextDueAmount: Double
)