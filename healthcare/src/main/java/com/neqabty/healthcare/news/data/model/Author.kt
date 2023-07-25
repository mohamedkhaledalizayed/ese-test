package com.neqabty.healthcare.news.data.model


import androidx.annotation.Keep

@Keep
data class Author(
    val code: String,
    val created_at: String,
    val entity_validations: List<EntityValidation>,
    val fawry_id: String,
    val id: Int,
    val image: Any,
    val links: Links,
    val mobile: Any,
    val name: String,
    val registration_notes: String,
    val requirements: List<Any>,
    val syndicate_status: Boolean,
    val type: Type,
    val updated_at: String
)