package com.neqabty.domain.entities

data class MedicalDirectoryLookupsEntity(
    var providerTypes: List<ServiceProviderType>? = null,
    var governs: List<Govern>? = null,
    var areas: List<Area>? = null,
    var degrees: List<DoctorDegree>? = null,
    var specializations: List<DoctorSpecialization>? = null
) {
    data class ServiceProviderType(
        var id: Int = 0,
        var name: String
    )

    data class Govern(
        var id: Int = 0,
        var name: String
    )

    data class Area(
        var id: Int = 0,
        var name: String,
        var govId: Int = 0
    )

    data class DoctorDegree(
        var id: Int = 0,
        var name: String
    )

    data class DoctorSpecialization(
        var id: Int = 0,
        var name: String
    )
}