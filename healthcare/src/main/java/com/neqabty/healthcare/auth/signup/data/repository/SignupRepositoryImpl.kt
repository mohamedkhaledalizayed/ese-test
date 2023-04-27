package com.neqabty.healthcare.auth.signup.data.repository


import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.neqabtymember.NeqabtyMemberModel
import com.neqabty.healthcare.auth.signup.data.model.syndicatemember.UserModel
import com.neqabty.healthcare.auth.signup.data.model.syndicates.EntityModel
import com.neqabty.healthcare.auth.signup.data.source.SignupDS
import com.neqabty.healthcare.auth.signup.domain.entity.UserEntity
import com.neqabty.healthcare.auth.signup.domain.entity.syndicate.SyndicateListEntity
import com.neqabty.healthcare.auth.signup.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val signupDS: SignupDS) : SignupRepository {
    override fun signup(signupParams: Any): Flow<Response<UserModel>> {
        return flow { emit(signupDS.signup(signupParams)) }
    }

    override fun signUpNeqabtyMember(neqabtySignupBody: NeqabtySignupBody): Flow<UserEntity> {
        return flow { emit(signupDS.signUpNeqabtyMember(neqabtySignupBody).toUserEntity()) }
    }

    override fun getSyndicates(): Flow<List<SyndicateListEntity>> {
        return flow {
            emit(signupDS.getSyndicates().filter { it.code != "e05" }.map { it.toSyndicateListEntity() })
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
