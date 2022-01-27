package com.neqabty.login.modules.home.domain.entity


import com.google.gson.annotations.SerializedName

data class ValidatorEntity(
    val email: Boolean = false,
    val id: Int = 0,
    val max: Any? = Any(),
    val min: Any? = Any(),
    val minLength: Int = 0,
    val pattern: String = "",
    val required: Boolean = false
)