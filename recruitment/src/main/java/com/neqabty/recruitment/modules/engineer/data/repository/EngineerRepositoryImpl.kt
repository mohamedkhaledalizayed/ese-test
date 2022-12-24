package com.neqabty.recruitment.modules.engineer.data.repository

import com.neqabty.recruitment.modules.engineer.data.datasource.EngineerDS
import com.neqabty.recruitment.modules.engineer.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.engineer.domain.*
import com.neqabty.recruitment.modules.engineer.domain.repository.EngineerRepository
import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.engineer.domain.entity.engineerdata.EngineerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EngineerRepositoryImpl @Inject constructor(private val engineerDS: EngineerDS):
    EngineerRepository {
    override fun getEngineerData(): Flow<EngineerEntity> {
        return flow {
            emit(engineerDS.getEngineerData().toEngineerEntity())
        }
    }

    override fun updateEngineerData(id: String, engineerBody: EngineerBody): Flow<EngineerEntity> {
        return flow {
            engineerDS.updateEngineerData(id, engineerBody)
        }
    }
}

private fun EngineerModel.toEngineerEntity(): EngineerEntity {
    return EngineerEntity(
        address = address,
        courses = courses.map { it.toCourse() },
        dateOfBirth = dateOfBirth,
        department = department.toDepartment(),
        educations = educations.map { it.toEducationEntity() },
        email = email,
        experiences = experiences.map { it.toExperienceEntity() },
        gender = gender,
        governorate = governorate.toGovernment(),
        grade =grade,
        graduationYear = graduationYear,
        languages = languages.map { it.toLanguage() },
        linkedInLink = linkedInLink,
        maritalStatus =  maritalStatus.toMaritalStatus(),
        membershipId = membershipId,
        membershipIdFile = membershipIdFile,
        militaryFile = militaryFile,
        militaryStatus = militaryStatus.toMilitaryStatus(),
        mobile = mobile,
        name = name,
        nationalId = nationalId,
        nationalIdFile = nationalIdFile,
        nationality = nationality.toNationality(),
        phone = phone,
        skills = skills.map { it.toSkills() },
        university = university.toUniversity(),
        workingStatus = workingStatus,
        yearsOfExperience = yearsOfExperience,
        zone = zone.toArea()
    )
}
