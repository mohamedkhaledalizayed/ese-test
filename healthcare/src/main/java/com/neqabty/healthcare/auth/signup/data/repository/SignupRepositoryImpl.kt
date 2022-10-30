package com.neqabty.healthcare.auth.signup.data.repository


import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.neqabtymember.NeqabtyMemberModel
import com.neqabty.healthcare.auth.signup.data.source.SignupDS
import com.neqabty.healthcare.auth.signup.domain.entity.UserEntity
import com.neqabty.healthcare.auth.signup.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val signupDS: SignupDS) : SignupRepository {

    override fun signUpNeqabtyMember(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity> {
        return flow { emit(signupDS.signUpNeqabtyMember(neqabtySignupBody).toUserEntity()) }
    }

}


fun NeqabtyMemberModel.toUserEntity(): UserEntity {
    return return UserEntity(
        email = email ?: "",
        token = token.key,
        fullname = fullname,
        id = id,
        image = image ?: "",
        mobile = mobile,
        nationalId = nationalId,
        entityName = entity.name,
        entityImage = entity.imageUrl,
        entityCode = entity.code)
}
