package com.neqabty.shealth.auth.otp.view

interface IVerifyPhoneListener {
    fun onSendClicked(phone: String)
    fun onReSendClicked()
    fun onCheckClicked(otp: String)
}