package com.neqabty.healthcare.retirement.data.model.inheritor


import androidx.annotation.Keep

@Keep
data class InheritorModel(
    val ActivateDate: String,
    val activate: Boolean,
    val mmashatid: Int,
    val name: String,
    val visa: String
)