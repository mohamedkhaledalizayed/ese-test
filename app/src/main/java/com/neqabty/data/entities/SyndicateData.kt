package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class SyndicateData(
    @field:SerializedName("syndicate_id")
    var id: Int = 0,
    @field:SerializedName("syndicate_desc_ar")
    var descAr: String?,
    @field:SerializedName("syndicate_desc_en")
    var descEn: String?,
    @field:SerializedName("syndicate_parent_id")
    var parentId: String?,
    @field:SerializedName("syndicate_level")
    var level: String?,
    @field:SerializedName("syndicate_address")
    var address: String?,
    @field:SerializedName("syndicate_phoneNumber")
    var phone: String?,
    @field:SerializedName("syndicate_email")
    var email: String?,
    @field:SerializedName("governorate_id")
    var governId: String?,
    @field:SerializedName("syndicate_logo")
    var logo: String?,
    @field:SerializedName("created_by")
    var createdBy: String?,
    @field:SerializedName("updated_by")
    var updatedBy: String?,
    @field:SerializedName("created_at")
    var createdAt: String?,
    @field:SerializedName("updated_at")
    var updatedAt: String?,
    @field:SerializedName("syndicate_fax")
    var fax: String?,
    @field:SerializedName("syndicate_mobile")
    var mobile: String?,
    @field:SerializedName("sub_sundicates")
    var subSyndicates: List<SyndicateData>?
) : Response()