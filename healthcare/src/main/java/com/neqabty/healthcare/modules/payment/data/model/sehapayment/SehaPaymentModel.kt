package com.neqabty.healthcare.modules.payment.data.model.sehapayment


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SehaPaymentModel(
    @SerializedName("account")
    val account: Int,
    @SerializedName("address")
    val address: Any,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("callback_fail_url")
    val callbackFailUrl: Any,
    @SerializedName("callback_success_url")
    val callbackSuccessUrl: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("delivery_method")
    val deliveryMethod: Any,
    @SerializedName("desired_payed_year")
    val desiredPayedYear: Any,
    @SerializedName("entity_branch")
    val entityBranch: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("membership_id")
    val membershipId: Int?,
    @SerializedName("message")
    val message: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("payment_source")
    val paymentSource: String,
    @SerializedName("service_action_code")
    val serviceActionCode: String,
    @SerializedName("service_code")
    val serviceCode: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("transaction")
    val transaction: SehaTransactionModel,
    @SerializedName("transaction_type")
    val transactionType: String,
    @SerializedName("updated_at")
    val updatedAt: String
)