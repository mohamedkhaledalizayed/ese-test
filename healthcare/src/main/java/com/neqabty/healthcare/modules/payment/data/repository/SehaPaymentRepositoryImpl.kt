package com.neqabty.healthcare.modules.payment.data.repository


import com.neqabty.healthcare.modules.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.modules.payment.data.model.sehapayment.SehaMobilePaymentPayloadModel
import com.neqabty.healthcare.modules.payment.data.model.sehapayment.SehaPaymentModel
import com.neqabty.healthcare.modules.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.healthcare.modules.payment.data.model.sehapayment.SehaTransactionModel
import com.neqabty.healthcare.modules.payment.data.source.SehaPaymentDS
import com.neqabty.healthcare.modules.payment.domain.entity.SehaMobilePaymentPayloadEntity
import com.neqabty.healthcare.modules.payment.domain.entity.SehaPayment
import com.neqabty.healthcare.modules.payment.domain.entity.SehaPaymentEntity
import com.neqabty.healthcare.modules.payment.domain.entity.SehaTransactionEntity
import com.neqabty.healthcare.modules.payment.domain.repository.SehaPaymentRepository
import com.neqabty.meganeqabty.payment.data.repository.toPaymentMethodsEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentmethods.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SehaPaymentRepositoryImpl @Inject constructor(private val paymentDS: SehaPaymentDS) :
    SehaPaymentRepository {

    override fun payment(paymentBody: SehaPaymentBody): Flow<SehaPaymentEntity> {
        return flow {
            emit(paymentDS.payment(paymentBody).toPaymentEntity())
        }
    }

    override fun getPaymentMethods(): Flow<List<PaymentMethodEntity>> {
        return flow {
            emit(paymentDS.getPaymentMethods().map { it.toPaymentMethodsEntity() })
        }
    }

}

fun SehaPaymentResponse.toPaymentEntity(): SehaPaymentEntity{
    return SehaPaymentEntity(
        mobilePaymentPayload = mobilePaymentPayload?.toMobilePaymentPayloadEntity(),
        payment = payment.toPayment()
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



