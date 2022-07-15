package com.neqabty.healthcare.modules.profile.domain.repository

import com.neqabty.healthcare.modules.profile.domain.entity.profile.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(phone: String): Flow<ProfileEntity>
}