package com.neqabty.healthcare.sustainablehealth.payment.data.repository


import com.neqabty.healthcare.sustainablehealth.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.sustainablehealth.payment.data.model.paymentmethods.DeliveryMethod
import com.neqabty.healthcare.sustainablehealth.payment.data.model.paymentmethods.PaymentMethodModel
import com.neqabty.healthcare.sustainablehealth.payment.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.healthcare.sustainablehealth.payment.data.model.sehapayment.SehaMobilePaymentPayloadModel
import com.neqabty.healthcare.sustainablehealth.payment.data.model.sehapayment.SehaPaymentModel
import com.neqabty.healthcare.sustainablehealth.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.healthcare.sustainablehealth.payment.data.model.sehapayment.SehaTransactionModel
import com.neqabty.healthcare.sustainablehealth.payment.data.source.SehaPaymentDS
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.SehaMobilePaymentPayloadEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.SehaPayment
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.SehaTransactionEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.DeliveryEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.PaymentEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.PaymentMethodEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.repository.SehaPaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class SehaPaymentRepositoryImpl @Inject constructor(private val paymentDS: SehaPaymentDS) :
    SehaPaymentRepository {

    override fun payment(paymentBody: SehaPaymentBody): Flow<Response<SehaPaymentResponse>> {
        return flow {
            emit(paymentDS.payment(paymentBody))
        }
    }

    override fun getPaymentMethods(code: String): Flow<PaymentEntity> {
        return flow {
            emit(paymentDS.getPaymentMethods(code).toPaymentEntity())
        }
    }

}

private fun PaymentMethodsResponse.toPaymentEntity(): PaymentEntity{
    return PaymentEntity(
        deliveryMethods = deliveryMethods.toDeliveryEntity(),
        paymentMethods = paymentMethods.map { it.toPaymentMethodsEntity() }
    )
}

private fun DeliveryMethod.toDeliveryEntity(): DeliveryEntity{
    return DeliveryEntity(
        type = type,
        id = id,
        method = method,
        price = price
    )
}

fun PaymentMethodModel.toPaymentMethodsEntity(): PaymentMethodEntity {
    return PaymentMethodEntity(
        displayName = displayName,
        gateway = gateway,
        id = id,
        isActive = isActive,
        name = name
    )
}

fun SehaPaymentModel.toPayment():SehaPayment{
    return SehaPayment(
        amount = amount,
        id = id,
        itemId = membershipId,
        paymentMethod = paymentMethod,
        paymentSource = paymentSource,
        serviceCode = serviceCode,
        transaction = transaction.toTransactionEntity()
    )
}

fun SehaTransactionModel.toTransactionEntity(): SehaTransactionEntity {
    return SehaTransactionEntity(
        paymentGatewayReferenceId = paymentGatewayReferenceId
    )
}

fun SehaMobilePaymentPayloadModel.toMobilePaymentPayloadEntity(): SehaMobilePaymentPayloadEntity {
    return SehaMobilePaymentPayloadEntity(
        callbackUrl = callbackUrl,
        countryCode = countryCode,
        currency = currency,
        expireAt = expireAt,
        merchantId = merchantId,
        merchantName = merchantName,
        payAmount = payAmount,
        paymentType = paymentType,
        productDescription = productDescription,
        productName = productName,
        publickey = publickey,
        reference = reference,
        userClientIP = userClientIP
    )
}



