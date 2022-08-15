package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalProceduresInquiryLookupsUI(
    var governs: List<Govern>? = null,
    var areas: List<Area>? = null,
    var categories: List<ProcedureCategory>? = null,
    var relationTypes: List<RelationType>? = null
): Parcelable {
    @Parcelize
    data class Govern(
        var id: Int = 0,
        var name: String
    ): Parcelable {
        override fun toString(): String {
            return name
        }
    }

    @Parcelize
    data class Area(
        var id: Int = 0,
        var name: String,
        var govId: Int = 0
    ): Parcelable {
        override fun toString(): String {
            return name
        }
    }

    @Parcelize
    data class ProcedureCategory(
        var id: Int = 0,
        var name: String,
        var parentCategoryId: Int? = 0
    ): Parcelable {
        override fun toString(): String {
            return name
        }
    }

    @Parcelize
    data class RelationType(
        var id: Int = 0,
        var name: String
    ): Parcelable {
        override fun toString(): String {
            return name
        }
    }
}