package com.neqabty.login.modules.home.domain.entity

data class ServiceEntity(
    val code: String = "",
    val createdAt: String = "",
    val domain: String = "",
    val id: Int = 0,
    val links: LinksXEntity = LinksXEntity(),
    val name: String = "",
    val requireRegistration: Boolean = false,
    val updatedAt: String = ""
)