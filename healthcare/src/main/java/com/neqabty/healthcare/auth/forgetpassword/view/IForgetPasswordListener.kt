package com.neqabty.healthcare.auth.forgetpassword.view

interface IForgetPasswordListener {
    fun onSendClicked()
    fun onCheckClicked(otp: String)
}