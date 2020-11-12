package com.neqabty.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class MedicalRenewalData(
        @field:SerializedName("engineer")
        var contact: ContactData? = null,
        @field:SerializedName("followerslist")
        var followers: List<FollowerItem>? = null
) : Response() {

    data class ContactData(
            @field:SerializedName("BENNAME")
            var name: String = "",
            @field:SerializedName("ContactID")
            var contactID: String = "",
            @field:SerializedName("SyndicateName")
            var syndicateName: String = ""
    )

    data class FollowerItem(
            @field:SerializedName("BENNAME")
            var name: String = "",
            @field:SerializedName("BenID")
            var id: Int = 0,
            @field:SerializedName("ISDELETED")
            var isDeleted: Boolean = false,
            @field:SerializedName("BIRTHDATE")
            var birthDate: String = "",
            @field:SerializedName("PIC")
            var pic: String? = ""

//            var pic: String? = "http://3.131.229.146:44392/images/news/8713684/15985232651598523265685f478781748b8_id.jpeg"
    )
}
