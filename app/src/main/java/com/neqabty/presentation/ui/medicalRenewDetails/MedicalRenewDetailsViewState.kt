package com.neqabty.presentation.ui.medicalRenewDetails

import com.neqabty.presentation.entities.DecryptionUI
import com.neqabty.presentation.entities.EncryptionUI
import com.neqabty.presentation.entities.MedicalRenewalPaymentUI
import com.neqabty.presentation.entities.MemberUI

data class MedicalRenewDetailsViewState(
    var isLoading: Boolean = true,
    var encryptionData: EncryptionUI? = null,
    var decryptionData: DecryptionUI? = null,
    var medicalRenewalPayment: MedicalRenewalPaymentUI? = null
)
