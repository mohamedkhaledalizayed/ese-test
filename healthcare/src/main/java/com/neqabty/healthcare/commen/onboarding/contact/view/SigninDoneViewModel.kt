package com.neqabty.healthcare.commen.onboarding.contact.view


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
import com.neqabty.healthcare.commen.onboarding.contact.domain.entity.CheckMemberEntity
import com.neqabty.healthcare.commen.onboarding.contact.domain.interactors.CheckMemberUseCase
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
class SigninDoneViewModel @Inject constructor(
    private val checkMemberUseCase: CheckMemberUseCase
) :
    BaseViewModel() {

    val checkMemberStatus = MutableLiveData<Resource<CheckMemberEntity>>()
    fun checkMemberStatus(nationalId: String){
        viewModelScope.launch(Dispatchers.IO){
            checkMemberStatus.postValue(Resource.loading(data = null))

            try {
                checkMemberUseCase.build(nationalId).collect(){
                    checkMemberStatus.postValue(Resource.success(data = it))
                }
            }catch (e: Throwable){
                checkMemberStatus.postValue(Resource.error(data = null, message = handleError(e)))
            }
        }
    }
}