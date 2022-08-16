package com.neqabty.signup.modules.signup.data.repository

import com.neqabty.signup.modules.signup.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.signup.data.model.SignupBody
import com.neqabty.signup.modules.signup.data.model.mappers.toUserEntity
import com.neqabty.signup.modules.signup.data.model.neqabtymember.NeqabtyMemberModel
import com.neqabty.signup.modules.signup.data.model.syndicates.EntityModel
import com.neqabty.signup.modules.signup.data.source.SignupDS
import com.neqabty.signup.modules.signup.domain.entity.SignupParams
import com.neqabty.signup.modules.signup.domain.entity.UserEntity
import com.neqabty.signup.modules.signup.domain.entity.syndicate.SyndicateListEntity
import com.neqabty.signup.modules.signup.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val signupDS: SignupDS) : SignupRepository {
    override fun signup(token: String, signupParams: SignupParams): Flow<UserEntity> {
        return flow { emit(signupDS.signup(token, signupParams.toSignupBody()).toUserEntity()) }
    }

    override fun signUpNeqabtyMember(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity> {
        return flow { emit(signupDS.signUpNeqabtyMember(neqabtySignupBody).toUserEntity()) }
    }

    override fun getSyndicates(): Flow<List<SyndicateListEntity>> {
        return flow {
            emit(signupDS.getSyndicates().map { it.toSyndicateListEntity() })
        }
    }
}

private fun EntityModel.toSyndicateListEntity(): SyndicateListEntity{
    return SyndicateListEntity(
        code = code,
        id = id,
        image = image,
        name = name
    )
}

fun NeqabtyMemberModel.toUserEntity(): UserEntity {
    return UserEntity(email ?: "", fullname, id, image ?: "", mobile, nationalId, entity.name, entity.code)
}

private fun SignupParams.toSignupBody(): SignupBody {
    return SignupBody(entityCode, email, membershipId, national_id)
}
