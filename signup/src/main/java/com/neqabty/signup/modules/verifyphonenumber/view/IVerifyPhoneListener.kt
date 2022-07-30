package com.neqabty.signup.modules.verifyphonenumber.view

interface IVerifyPhoneListener {
    fun onSendClicked(phone: String)
    fun onCheckClicked(otp: String)
}