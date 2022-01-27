package com.neqabty.login.modules.home.domain.entity



data class RequirementEntity(
    val entity: Int = 0,
    val id: Int = 0,
    val label: Any = Any(),
    val name: String = "",
    val optional: Boolean = false,
    val type: String = "",
    val validators: Any = Any()
)