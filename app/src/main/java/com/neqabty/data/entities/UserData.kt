package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class UserData(
    @field:SerializedName("syndicate_user_id")
    var id: Int = 0,

    @field:SerializedName("syndicate_user_fname") //
    var fName: String? = "",

    @field:SerializedName("syndicate_user_lname") //
    var lName: String? = "",

    @field:SerializedName("syndicate_user_email") //
    var email: String? = "",

    @field:SerializedName("syndicate_user_password") //
    var password: String? = "",

    @field:SerializedName("syndicate_user_number")
    var number: String? = "",

    @field:SerializedName("main_syndicate_id") //
    var mainSyndicateId: String? = "",

    @field:SerializedName("sub_syndicate_id") //
    var subSyndicateId: String? = "",

    @field:SerializedName("syndicate_user_mobile") //
    var syndicateUserMobile: String? = "",

    @field:SerializedName("user_token") //
    var userToken: String? = "",

    @field:SerializedName("verification_code") //
    var verificationCode: String? = "",

    @field:SerializedName("created_at") //
    var createdAt: String? = "",

    @field:SerializedName("updated_at") //
    var updatedAt: String? = ""
) : Response()