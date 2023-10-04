package com.neqabty.healthcare.payment.domain.entity.inquiryresponse



data class GatewaysEntity(
    val displayName: String,
    val endpointUrl: String,
    val gateway: String,
    val id: Int,
    val isActive: Boolean,
    val name: String
)