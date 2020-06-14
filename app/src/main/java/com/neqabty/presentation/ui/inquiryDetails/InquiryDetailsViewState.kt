package com.neqabty.presentation.ui.inquiryDetails

import com.neqabty.presentation.entities.DecryptionUI
import com.neqabty.presentation.entities.EncryptionUI
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.ServiceUI

data class InquiryDetailsViewState(
    var isLoading: Boolean = true,
    var encryptionData: EncryptionUI? = null,
    var decryptionData: DecryptionUI? = null,
    var member: MemberUI? = null
)