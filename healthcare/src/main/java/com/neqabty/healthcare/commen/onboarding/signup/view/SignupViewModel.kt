package com.neqabty.healthcare.commen.onboarding.signup.view


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.auth.otp.data.model.CheckOTPBody
import com.neqabty.healthcare.auth.otp.data.model.SendOTPBody
import com.neqabty.healthcare.auth.otp.domain.entity.sendotp.OTPEntity
import com.neqabty.healthcare.auth.otp.domain.usecases.VerifyPhoneUseCase
import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.syndicatemember.UserModel
import com.neqabty.healthcare.auth.signup.domain.interactors.SignupUseCase
import com.neqabty.healthcare.auth.signup.presentation.model.UserUIModel
import com.neqabty.healthcare.auth.signup.presentation.model.mappers.toUserUIModel
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.commen.syndicates.domain.interactors.GetSyndicateUseCase
import com.neqabty.healthcare.core.ui.BaseViewModel
import com.neqabty.healthcare.core.utils.AppUtils
import com.neqabty.healthcare.core.utils.Resource
import com.neqabty.healthcare.mega.payment.domain.interactors.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val verifyPhoneUseCase: VerifyPhoneUseCase,
    private val getSyndicateUseCase: GetSyndicateUseCase,
    private val signupUseCase: SignupUseCase
) :
    BaseViewModel() {

    val otp = MutableLiveData<Resource<OTPEntity>>()
    fun sendOTP(sendOTPBody: SendOTPBody){
        viewModelScope.launch(Dispatchers.IO){
            otp.postValue(Resource.loading(data = null))

            try {
                verifyPhoneUseCase.build(sendOTPBody).collect(){
                    otp.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                otp.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    val otpStatus = MutableLiveData<Resource<Boolean>>()
    fun checkOTP(checkOTPBody: CheckOTPBody){
        viewModelScope.launch(Dispatchers.IO){
            otpStatus.postValue(Resource.loading(data = null))

            try {
                verifyPhoneUseCase.build(checkOTPBody).collect(){
                    otpStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                otpStatus.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    val generalUser = MutableLiveData<Resource<UserUIModel>>()
    fun generalUserSignup(data: NeqabtySignupBody) {
        generalUser.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signupUseCase.build(data).collect {
                    generalUser.postValue(Resource.success(data = it.toUserUIModel()))
                }
            } catch (e: Throwable) {
                generalUser.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }

    val syndicates = MutableLiveData<Resource<List<SyndicateEntity>>>()
    fun getSyndicates() {
        syndicates.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateUseCase.build().collect {
                    syndicates.postValue(Resource.success(data = it))
                }
            }catch (exception:Throwable){
                syndicates.postValue(Resource.error(data = null, message = AppUtils().handleError(exception)))
            }
        }
    }

}