package com.neqabty.shealth.auth.login.data.model

import androidx.annotation.Keep

@Keep
data class LoginBody(val mobile: String, val password: String)
