package com.neqabty.healthcare.onboarding.signup.data

import com.neqabty.healthcare.syndicates.domain.entity.SyndicateEntity


class SignupData {
    companion object {
        var syndicatesList = mutableListOf<SyndicateEntity>()
        var syndicateID: String = ""
        var syndicateName: String = ""
    }
}