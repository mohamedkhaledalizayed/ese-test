package com.neqabty.data.mappers

import com.neqabty.data.entities.ProfileData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ProfileEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileDataEntityMapper @Inject constructor() : Mapper<ProfileData, ProfileEntity>() {

    override fun mapFrom(from: ProfileData): ProfileEntity {
        return ProfileEntity(
                invitations = from.invitations,
                code = from.code,
                name = from.name,
                governerate = from.governerate,
                syndicate = from.syndicate,
                section = from.section,
                image = from.image
        )
    }
}
