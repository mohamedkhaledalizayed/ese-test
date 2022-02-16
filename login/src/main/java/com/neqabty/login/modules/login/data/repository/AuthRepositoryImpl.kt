package com.neqabty.login.modules.login.data.repository

import android.util.Log
import com.neqabty.login.modules.login.data.model.UserModel
import com.neqabty.login.modules.login.data.source.AuthDS
import com.neqabty.login.modules.login.domain.entity.UserEntity
import com.neqabty.login.modules.login.domain.repository.AuthRepository
import com.neqabty.news.modules.home.data.model.News
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDS: AuthDS) : AuthRepository {
    override fun login(mobile: String, password: String): Flow<UserEntity> {
        return flow { emit(authDS.login(mobile, password).toUserEntity()) }
    }

    private fun UserModel.toUserEntity(): UserEntity {
        return UserEntity(
            email, fullname, id, image, mobile, nationalId, password
        )
    }
}