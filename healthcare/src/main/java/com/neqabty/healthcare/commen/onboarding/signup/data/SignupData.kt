package com.neqabty.healthcare.commen.onboarding.signup.data

import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity


class SignupData {
    companion object {
        var syndicatesList = mutableListOf<SyndicateEntity>()
        var syndicateID: String = ""
        var syndicateName: String = ""
    }
}