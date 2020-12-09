package com.neqabty.presentation.ui.inquiryDetails

import com.neqabty.presentation.entities.DecryptionUI
import com.neqabty.presentation.entities.EncryptionUI
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MemberUI

data class InquiryDetailsViewState(
    var isLoading: Boolean = true,
    var encryptionData: EncryptionUI? = null,
    var decryptionData: DecryptionUI? = null,
    var medicalRenewalPayment: MedicalRenewalPaymentUI? = null
)
