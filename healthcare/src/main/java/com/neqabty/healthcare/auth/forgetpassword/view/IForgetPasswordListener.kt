package com.neqabty.healthcare.auth.forgetpassword.view

interface IForgetPasswordListener {
    fun onSendClicked(phone: String)
    fun onCheckClicked(otp: String)
}