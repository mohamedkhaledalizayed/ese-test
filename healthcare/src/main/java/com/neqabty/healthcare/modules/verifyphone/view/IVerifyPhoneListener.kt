package com.neqabty.healthcare.modules.verifyphone.view

interface IVerifyPhoneListener {
    fun onSendClicked(phone: String)
    fun onCheckClicked(otp: String)
}