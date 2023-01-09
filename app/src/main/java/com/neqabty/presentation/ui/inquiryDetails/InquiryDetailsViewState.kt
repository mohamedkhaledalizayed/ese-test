package com.neqabty.presentation.ui.inquiryDetails

import com.neqabty.presentation.entities.*

data class InquiryDetailsViewState(
    var isLoading: Boolean = false,
    var encryptionData: EncryptionUI? = null,
    var decryptionData: DecryptionUI? = null,
    var medicalRenewalPayment: MedicalRenewalPaymentUI? = null,
    var paymentRequestUI: PaymentRequestUI? = null,
    var fawryTransactionUI: FawryTransactionUI? = null
)
