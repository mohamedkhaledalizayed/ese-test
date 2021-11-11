package com.neqabty.presentation.entities

data class MedicalDirectoryLookupsUI(
    var providerTypes: List<ServiceProviderType>? = null,
    var governs: List<Govern>? = null,
    var areas: List<Area>? = null,
    var degrees: List<DoctorDegree>? = null,
    var specializations: List<DoctorSpecialization>? = null
) {
    data class ServiceProviderType(
        var id: Int = 0,
        var name: String
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }

    data class Govern(
        var id: Int = 0,
        var name: String
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }

    data class Area(
        var id: Int = 0,
        var name: String,
        var govId: Int = 0
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }

    data class DoctorDegree(
        var id: Int = 0,
        var name: String
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }

    data class DoctorSpecialization(
        var id: Int = 0,
        var name: String
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }
}