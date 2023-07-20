package com.neqabty.healthcare.onboarding.contact.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckMemberEntity(
    val authorized: Boolean,
    val ocrStatus: String?,
    val message: String?,
    val ocrs: List<Ocr>
): Parcelable

@Parcelize
data class Ocr(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val ocr_uuid: String,
    val image_type: String,
    val result: Result,
    val account: Int
): Parcelable

@Parcelize
data class Result(
    val extractedInfo: ExtractedInfo,
    val completed: Boolean
): Parcelable

@Parcelize
data class ExtractedInfo(
    val gender: String,
    val religion: String,
    val correctDoc: String,
    val expiry_date: String,
    val national_id: String,
    val spouse_name: String,
    val profession_1: String,
    val profession_2: String,
    val release_date: String,
    val marital_status: String,
    val area: String,
    val street: String,
    val full_name: String,
    val birth_date: String,
    val first_name: String,
    val governorate: String,
    val serial_number: String,
    val serial_validation: String
): Parcelable