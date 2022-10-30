package com.neqabty.shealth.sustainablehealth.receipt.data.repository


import com.neqabty.shealth.sustainablehealth.receipt.data.model.paymentmethods.PaymentMethodModel
import com.neqabty.shealth.sustainablehealth.receipt.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.shealth.sustainablehealth.receipt.data.source.PaymentDS
import com.neqabty.shealth.sustainablehealth.receipt.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.shealth.sustainablehealth.receipt.domain.repository.PaymentRepository
import com.neqabty.mega.payment.domain.entity.paymentmethods.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val paymentDS: PaymentDS) :
    PaymentRepository {

    override fun getPaymentStatus(referenceCode: String): Flow<PaymentStatusEntity> {
        return flow {
            emit((paymentDS.getPaymentStatus(referenceCode).toPaymentStatusEntity()))
        }
    }

}

fun PaymentStatusModel.toPaymentStatusEntity(): PaymentStatusEntity {
    return PaymentStatusEntity(
        entity = account.entity.name,
        entityPaymentStatus = entityPaymentStatus,
        gatewayReferenceId = gatewayReferenceId,
        id = id,
        itemId = membershipId,
        mobile = account.mobile,
        netAmount = netAmount,
        serviceAction = serviceAction.name,
        totalAmount = totalAmount,
        totalFees = totalFees,
        imageUrl = account.entity.imageUrl,
        member_name = account.fullname,
        createdAt = createdAt
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


