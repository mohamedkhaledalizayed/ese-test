package com.neqabty.healthcare.payment.data.repository


import com.neqabty.healthcare.payment.data.model.branches.EntityBranche
import com.neqabty.healthcare.payment.data.model.branches.EntityModel
import com.neqabty.healthcare.payment.data.model.inquiryresponse.InquiryModel
import com.neqabty.healthcare.payment.data.model.payment.PaymentModel
import com.neqabty.healthcare.payment.data.model.paymentstatus.PaymentStatusModel
import com.neqabty.healthcare.payment.data.model.services.ServiceModel
import com.neqabty.healthcare.payment.data.model.servicesaction.ServiceAction
import com.neqabty.healthcare.payment.data.source.PaymentDS
import com.neqabty.healthcare.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.payment.domain.entity.branches.Entity
import com.neqabty.healthcare.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.healthcare.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.healthcare.payment.domain.entity.services.ServicesListEntity
import com.neqabty.healthcare.payment.domain.repository.PaymentRepository
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

    override fun getPaymentDetails(id: String, code: String, number: String): Flow<Response<InquiryModel>> {
        return flow {
            emit(paymentDS.getPaymentDetails(id, code, number))
        }
    }

    override fun getPaymentStatus(referenceCode: String): Flow<PaymentStatusEntity> {
        return flow {
            emit((paymentDS.getPaymentStatus(referenceCode).toPaymentStatusEntity()))
        }
    }

    override fun payment(paymentBody: Any): Flow<PaymentEntity> {
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

fun PaymentModel.toPaymentEntity(): PaymentEntity{
    return PaymentEntity(
        address = address ?: "",
        cashierUrl = cashier_url ?: "",
        deliveryFees = delivery_fees ?: "",
        deliveryMethod = delivery_method ?: 0,
        entityRefNum = entity_ref_num ?: "",
        fees = fees ?: "",
        id = id,
        reference = reference ?: "",
        membershipId = membership_id ?: "",
        paymentGateway = payment_gateway ?: 0,
        paymentGatewayTransactionNum = payment_gateway_transaction_num ?: "",
        paymentMethod = payment_method ?: "",
        paymentSource = payment_source ?: "",
        refund = refund,
        service = service,
        serviceAction = service_action,
        status = status ?: "",
        totalAmount = total_amount ?: "",
        transactionType = transaction_type ?: "",
        publicKey = public_key ?: "",
        callBackURL = callBackURL ?: "",
        expireAt = expireAt ?: "30",
        merchantId = merchant_id ?: ""
    )
}


fun PaymentStatusModel.toPaymentStatusEntity(): PaymentStatusEntity {
    return PaymentStatusEntity(
        entity = entity ?: "",
        gatewayReferenceId = gateway_reference_id,
        itemId = membership_id ?: "",
        mobile = account.mobile,
        netAmount = net_amount,
        serviceAction = service_action.name ?: "",
        totalAmount = total_amount,
        totalFees = total_fees,
        member_name = account.fullname ?: "",
        createdAt = created_at
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


