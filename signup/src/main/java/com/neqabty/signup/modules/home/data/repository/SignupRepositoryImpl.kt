package com.neqabty.signup.modules.home.data.repository

import com.neqabty.signup.modules.home.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.home.data.model.SignupBody
import com.neqabty.signup.modules.home.data.model.mappers.toUserEntity
import com.neqabty.signup.modules.home.data.model.neqabtymember.NeqabtyMemberModel
import com.neqabty.signup.modules.home.data.model.syndicates.EntityModel
import com.neqabty.signup.modules.home.data.source.SignupDS
import com.neqabty.signup.modules.home.domain.entity.SignupParams
import com.neqabty.signup.modules.home.domain.entity.UserEntity
import com.neqabty.signup.modules.home.domain.entity.syndicate.SyndicateListEntity
import com.neqabty.signup.modules.home.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val signupDS: SignupDS) : SignupRepository {
    override fun signup(signupParams: SignupParams): Flow<UserEntity> {
        return flow { emit(signupDS.signup(signupParams.toSignupBody()).toUserEntity()) }
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
    return SignupBody(entityCode, email, membershipId, mobile, national_id)
}
