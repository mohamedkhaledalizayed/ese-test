package com.neqabty.login.modules.login.data.repository

import com.neqabty.login.modules.login.data.model.*
import com.neqabty.login.modules.login.data.source.AuthDS
import com.neqabty.login.modules.login.domain.entity.AccountEntity
import com.neqabty.login.modules.login.domain.entity.Entity
import com.neqabty.login.modules.login.domain.entity.User
import com.neqabty.login.modules.login.domain.entity.UserEntity
import com.neqabty.login.modules.login.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDS: AuthDS) : AuthRepository {
    override fun login(mobile: String, password: String): Flow<UserEntity> {
        return flow { emit(authDS.login(mobile, password).toUserEntity()) }
    }
}

private fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        token = key,
        user = user.toUser()
    )
}

private fun UserData.toUser(): User {
    return User(
        membershipId = membershipId.toString(),
        account = account.toAccountEntity()
    )
}

private fun Account.toAccountEntity(): AccountEntity {
    return AccountEntity(
        email = email,
        entity = entity.toEntity(),
        fullName = fullname,
        id = id,
        verifiedAccount = verifiedAccount,
        image = image,
        mobile = mobile,
        nationalId = nationalId
    )
}


private fun EntityModel.toEntity(): Entity {
    return Entity(
        code = code,
        id = id,
        name = name,
        type = type,
        image = imageUrl

    )
}