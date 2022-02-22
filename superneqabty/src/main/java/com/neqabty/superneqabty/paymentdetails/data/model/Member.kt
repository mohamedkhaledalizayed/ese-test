package com.neqabty.superneqabty.paymentdetails.data.model


import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("account")
    val account: Account,
    @SerializedName("entity")
    val entity: Entity
)