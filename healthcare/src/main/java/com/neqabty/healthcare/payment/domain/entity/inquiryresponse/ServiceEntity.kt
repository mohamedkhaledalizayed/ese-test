package com.neqabty.healthcare.payment.domain.entity.inquiryresponse



data class ServiceEntity(
    val code: String,
    val id: Int,
    val isActive: Boolean,
    val name: String
)