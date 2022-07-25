package com.neqabty.meganeqabty.payment.domain.entity.inquiryresponse


data class ReceiptDataEntity(
    val member: MemberEntity,
    val receipt: ReceiptEntity?,
    val service: ServiceEntity,
    val deliveryMethodsEntity: List<DeliveryMethodsEntity>,
    val title: String
)