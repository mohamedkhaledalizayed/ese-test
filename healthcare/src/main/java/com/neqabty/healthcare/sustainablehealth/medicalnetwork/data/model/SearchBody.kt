package com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model

import androidx.annotation.Keep

@Keep
data class SearchBody(
    val governorate_id: String = "",
    val area_id: String = "",
    val service_provider_type_id: String = "",
    val name: String = "",
    val profession_id: String = "",
    val page: Int = 0,
    val has_qrcode: Int = 0
    )
