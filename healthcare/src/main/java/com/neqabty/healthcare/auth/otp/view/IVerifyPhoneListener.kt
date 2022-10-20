package com.neqabty.healthcare.auth.otp.view

interface IVerifyPhoneListener {
    fun onSendClicked(phone: String)
    fun onReSendClicked()
    fun onCheckClicked(otp: String)
}