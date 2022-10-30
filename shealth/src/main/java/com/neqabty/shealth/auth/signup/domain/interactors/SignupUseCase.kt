package com.neqabty.shealth.auth.signup.domain.interactors


import com.neqabty.shealth.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.shealth.auth.signup.domain.entity.UserEntity
import com.neqabty.shealth.auth.signup.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val signupRepository: SignupRepository) {

    fun build(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity> {
        return signupRepository.signUpNeqabtyMember(neqabtySignupBody)
    }

}