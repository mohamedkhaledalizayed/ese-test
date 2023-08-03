package com.neqabty.healthcare.retirement.data.model.pension


import androidx.annotation.Keep

@Keep
data class PensionModel(
    val exchanges: List<Exchange>,
    val mmashatid: Int,
    val name: String,
    val subcommitteid: String,
    val syndicateid: String
)