package com.neqabty.healthcare.auth.login.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Entity(
    val code: String,
    val id: Int,
    val name: String,
    val type: String,
    val image: String?

): Parcelable