package com.neqabty.healthcare.mega.payment.data.model.payment


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PaymentModel(
    @SerializedName("account")
    val account: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("callback_fail_url")
    val callbackFailUrl: Any,
    @SerializedName("callback_success_url")
    val callbackSuccessUrl: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("delivery_method")
    val deliveryMethod: Int,
    @SerializedName("desired_payed_year")
    val desiredPayedYear: Any,
    @SerializedName("entity_branch")
    val entityBranch: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("membership_id")
    val membershipId: Int,
    @SerializedName("message")
    val message: Any,
    @SerializedName("mobile")
    val mobile: Any,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("service_action_code")
    val serviceActionCode: String,
    @SerializedName("service_code")
    val serviceCode: String,
    @SerializedName("status")
    val status: Any,
    @SerializedName("transaction")
    val transaction: Transaction,
    @SerializedName("transaction_type")
    val transactionType: String,
    @SerializedName("updated_at")
    val updatedAt: String
)