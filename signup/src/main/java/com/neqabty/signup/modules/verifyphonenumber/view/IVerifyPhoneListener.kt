package com.neqabty.signup.modules.verifyphonenumber.view

interface IVerifyPhoneListener {
    fun onSendClicked(phone: String)
    fun onReSendClicked(token: String)
    fun onCheckClicked(otp: String)
}