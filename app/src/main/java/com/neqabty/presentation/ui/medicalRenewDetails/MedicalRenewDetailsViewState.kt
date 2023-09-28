package com.neqabty.presentation.ui.medicalRenewDetails

import com.neqabty.presentation.entities.*

data class MedicalRenewDetailsViewState(
    var isLoading: Boolean = true,
    var encryptionData: EncryptionUI? = null,
    var decryptionData: DecryptionUI? = null,
    var medicalRenewalPayment: MedicalRenewalPaymentUI? = null,
    var paymentRequestUI: PaymentRequestUI? = null,
    var fawryTransactionUI: FawryTransactionUI? = null
)
