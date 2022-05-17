package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.ProfileEntity
import com.neqabty.presentation.entities.ProfileUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileEntityUIMapper @Inject constructor() : Mapper<ProfileEntity, ProfileUI>() {

    override fun mapFrom(from: ProfileEntity): ProfileUI {
        return ProfileUI(
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
