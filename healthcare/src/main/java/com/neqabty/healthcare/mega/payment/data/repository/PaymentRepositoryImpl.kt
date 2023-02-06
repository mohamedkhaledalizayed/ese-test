package com.neqabty.healthcare.mega.payment.data.repository


import com.neqabty.healthcare.mega.payment.data.model.PaymentBody
import com.neqabty.healthcare.mega.payment.data.model.branches.EntityBranche
import com.neqabty.healthcare.mega.payment.data.model.branches.EntityModel
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.*
import com.neqabty.healthcare.mega.payment.data.model.payment.PaymentModel
import com.neqabty.healthcare.sustainablehealth.payment.data.model.paymentmethods.PaymentMethodModel
import com.neqabty.healthcare.mega.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.healthcare.mega.payment.data.model.services.*
import com.neqabty.healthcare.mega.payment.data.model.servicesaction.ServiceAction
import com.neqabty.healthcare.mega.payment.data.source.PaymentDS
import com.neqabty.healthcare.mega.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.mega.payment.domain.entity.branches.Entity
import com.neqabty.healthcare.mega.payment.domain.entity.inquiryresponse.*
import com.neqabty.healthcare.mega.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.mega.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.healthcare.mega.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.healthcare.mega.payment.domain.entity.services.*
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import com.neqabty.mega.payment.domain.entity.paymentmethods.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val paymentDS: PaymentDS) :
    PaymentRepository {

    override fun getServices(): Flow<List<ServicesListEntity>> {
        return flow {
            emit(paymentDS.getServices().map { it.toServicesListEntity() })
        }
    }

    override fun getServiceActions(code: String): Flow<List<ServiceActionsEntity>> {
        return flow {
            emit(paymentDS.getServiceActions(code).map { it.toServiceActionsEntity() })
        }
    }

    override fun getPaymentDetails(id: String, code: String, number: String): Flow<Response<ReceiptResponse>> {
        return flow {
            emit(paymentDS.getPaymentDetails(id, code, number))
        }
    }

    override fun getPaymentStatus(referenceCode: String): Flow<PaymentStatusEntity> {
        return flow {
            emit((paymentDS.getPaymentStatus(referenceCode).toPaymentStatusEntity()))
        }
    }

    override fun payment(paymentBody: PaymentBody): Flow<PaymentEntity> {
        return flow {
            emit(paymentDS.payment(paymentBody).toPaymentEntity())
        }
    }

    override fun getBranches(): Flow<List<BranchesEntity>> {
        return flow {
            emit(paymentDS.getBranches().map { it.toBranchesEntity() })
        }
    }

}

private fun EntityBranche.toBranchesEntity():BranchesEntity{
    return BranchesEntity(
        id = id,
        entity = entity.toEntity(),
        address = address,
        city = city
    )
}

private fun EntityModel.toEntity(): Entity{
    return Entity(
        code = code,
        id = id,
        image = image,
        name = name,

    )
}
fun ReceiptResponse.toReceiptDataEntity(): ReceiptDataEntity{
    return ReceiptDataEntity(
        member = member.toMemberEntity(),
        receipt = receipt?.toReceiptEntity(),
        service = service.toServiceEntity(),
        deliveryMethodsEntity = deliveryMethods.map { it.toDeliveryMethodsEntity() },
        gatewaysList = gatewaysData.map { it.toGatewaysEntity() },
        title = title
    )
}

private fun GatewaysData.toGatewaysEntity(): GatewaysEntity{
    return GatewaysEntity(
        displayName = displayName,
        endpointUrl = endpointUrl,
        gateway = gateway,
        id = id,
        isActive = isActive,
        name = name
    )
}

private fun DeliveryMethod.toDeliveryMethodsEntity(): DeliveryMethodsEntity{
    return DeliveryMethodsEntity(
        createdAt = createdAt,
        id = id,
        method = method,
        price = price,
        updatedAt = updatedAt
    )
}

fun Member.toMemberEntity(): MemberEntity{
    return MemberEntity(
        name = name
    )
}

fun Receipt.toReceiptEntity(): ReceiptEntity{
    return ReceiptEntity(
        lastFeeYear = lastFeeYear,
        currentFeeYear = currentFeeYear,
        cardPrice = cardPrice,
        lateSubscriptions =lateSubscriptions,
        delayFine = delayFine,
        netAmount = netAmount,
        fees = fees,
        totalPrice = totalPrice
    )
}

fun Service.toServiceEntity(): ServiceEntity {
    return ServiceEntity(
        code = code,
        id = id,
        isActive = isActive,
        name = name
    )
}

fun PaymentModel.toPaymentEntity(): PaymentEntity{
    return PaymentEntity(
        address,
        cashierUrl,
        deliveryFees,
        deliveryMethod,
        entityRefNum,
        fees,
        id,
        membershipId,
        paymentGateway,
        paymentGatewayTransactionNum,
        paymentMethod,
        paymentSource,
        refund,
        service,
        serviceAction,
        status,
        totalAmount,
        transactionType
    )
}


fun PaymentStatusModel.toPaymentStatusEntity(): PaymentStatusEntity {
    return PaymentStatusEntity(
        entity = account.entity.name,
        entityType = account.entity.type,
        serviceCategory = serviceAction.service.serviceCategory.name,
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

fun ServiceModel.toServicesListEntity(): ServicesListEntity {
    return ServicesListEntity(
        code,
        name
    )
}

fun ServiceAction.toServiceActionsEntity(): ServiceActionsEntity {
    return ServiceActionsEntity(
        code,
        name
    )
}


