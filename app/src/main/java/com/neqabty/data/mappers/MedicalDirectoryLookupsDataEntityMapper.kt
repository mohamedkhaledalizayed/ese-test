package com.neqabty.data.mappers

import com.neqabty.data.entities.MedicalDirectoryLookupsData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.MedicalDirectoryLookupsEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalDirectoryLookupsDataEntityMapper @Inject constructor() : Mapper<MedicalDirectoryLookupsData, MedicalDirectoryLookupsEntity>() {

    override fun mapFrom(from: MedicalDirectoryLookupsData): MedicalDirectoryLookupsEntity {
        var medicalDirectoryLookupsEntity = MedicalDirectoryLookupsEntity()

        from.providerTypes?.let {
            var providerTypes: List<MedicalDirectoryLookupsEntity.ServiceProviderType> = it.map { providerItem ->
                return@map MedicalDirectoryLookupsEntity.ServiceProviderType(
                    id = providerItem.id,
                    name = providerItem.name ?: ""
                )
            }
            medicalDirectoryLookupsEntity.providerTypes = providerTypes
        }

        from.governs?.let {
            var governs: List<MedicalDirectoryLookupsEntity.Govern> = it.map { governItem ->
                return@map MedicalDirectoryLookupsEntity.Govern(
                    id = governItem.id,
                    name = governItem.name ?: ""
                )
            }
            medicalDirectoryLookupsEntity.governs = governs
        }

        from.areas?.let {
            var areas: List<MedicalDirectoryLookupsEntity.Area> = it.map { areaItem ->
                return@map MedicalDirectoryLookupsEntity.Area(
                    id = areaItem.id,
                    name = areaItem.name ?: "",
                    govId = areaItem.govId
                )
            }
            medicalDirectoryLookupsEntity.areas = areas
        }

        from.degrees?.let {
            var degrees: List<MedicalDirectoryLookupsEntity.DoctorDegree> = it.map { degreeItem ->
                return@map MedicalDirectoryLookupsEntity.DoctorDegree(
                    id = degreeItem.id,
                    name = degreeItem.name ?: ""
                )
            }
            medicalDirectoryLookupsEntity.degrees = degrees
        }

        from.specializations?.let {
            var specializations: List<MedicalDirectoryLookupsEntity.DoctorSpecialization> = it.map { specializationItem ->
                return@map MedicalDirectoryLookupsEntity.DoctorSpecialization(
                    id = specializationItem.id,
                    name = specializationItem.name ?: ""
                )
            }
            medicalDirectoryLookupsEntity.specializations = specializations
        }

        return medicalDirectoryLookupsEntity
    }
}