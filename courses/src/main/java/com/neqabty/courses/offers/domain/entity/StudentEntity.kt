package com.neqabty.courses.offers.domain.entity


data class StudentEntity(
    val fullName: String,
    val id: Int,
    val membershipId: Int,
    val stdCategoryEntity: StudentCategoryEntity
)