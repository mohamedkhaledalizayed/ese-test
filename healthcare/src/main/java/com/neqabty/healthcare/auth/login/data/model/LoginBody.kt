package com.neqabty.healthcare.auth.login.data.model

import androidx.annotation.Keep

@Keep
data class LoginBody(val mobile: String, val password: String)
