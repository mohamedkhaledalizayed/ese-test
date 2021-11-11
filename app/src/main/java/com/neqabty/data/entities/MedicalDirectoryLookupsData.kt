package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class MedicalDirectoryLookupsData(
    @field:SerializedName("ServiceProviderTypeList")
    var providerTypes: List<ServiceProviderType>? = null,
    @field:SerializedName("GovernorateList")
    var governs: List<Govern>? = null,
    @field:SerializedName("PoliceStation")
    var areas: List<Area>? = null,
    @field:SerializedName("DoctorDegreeList")
    var degrees: List<DoctorDegree>? = null,
    @field:SerializedName("MedicalSpecialityList")
    var specializations: List<DoctorSpecialization>? = null
) : Response() {
    data class ServiceProviderType(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("ServiceProviderTypeName")
        var name: String?
    )

    data class Govern(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("ARABICNAME")
        var name: String?
    )

    data class Area(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("StationNameArb")
        var name: String?,
        @field:SerializedName("FK_GovID")
        var govId: Int = 0
    )

    data class DoctorDegree(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("DoctorDegreeName")
        var name: String?
    )

    data class DoctorSpecialization(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("MedicalSpecialityName")
        var name: String?
    )
}