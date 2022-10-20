package com.neqabty.healthcare.auth.otp.data.api



import com.neqabty.healthcare.auth.otp.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.otp.data.model.SendOTPBody
import com.neqabty.healthcare.auth.otp.data.model.checkotp.CheckOTPModel
import com.neqabty.healthcare.auth.otp.data.model.recaptcha.VerifyRecaptcha
import com.neqabty.healthcare.auth.otp.data.model.sendotp.OTPModel
import retrofit2.http.*

@JvmSuppressWildcards
interface VerifyPhoneApi {

    @POST("send_otp")
    suspend fun sendOTP(@Body sendOTPBody: SendOTPBody): OTPModel

    @POST("get_otp_token")
    suspend fun checkOTP(@Body checkOTPBody: CheckOTPBody): CheckOTPModel

    @FormUrlEncoded
    @POST
    suspend fun verifyRecaptcha(
        @Url url: String = "https://www.google.com/recaptcha/api/siteverify",
        @Field("secret") secret: String = "6LdMpG8iAAAAAG8uE1YkOBog2uqyFfl-vNtErF9U",
        @Field("") token: String): VerifyRecaptcha

}