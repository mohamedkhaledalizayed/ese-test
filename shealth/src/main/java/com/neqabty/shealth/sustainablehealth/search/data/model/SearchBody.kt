package com.neqabty.shealth.sustainablehealth.search.data.model

import androidx.annotation.Keep

@Keep
data class SearchBody(
    val governorate_id: String,
    val service_provider_type_id: String,
    val name: String,
    val profession_id: String
    )
