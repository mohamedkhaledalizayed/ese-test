package com.neqabty.presentation.ui.medicalRenewDetails

import com.neqabty.presentation.entities.*

data class MedicalRenewDetailsViewState(
    var isLoading: Boolean = false,
    var encryptionData: EncryptionUI? = null,
    var decryptionData: DecryptionUI? = null,
    var paymentRequestUI: PaymentRequestUI? = null,
    var fawryTransactionUI: FawryTransactionUI? = null
)
