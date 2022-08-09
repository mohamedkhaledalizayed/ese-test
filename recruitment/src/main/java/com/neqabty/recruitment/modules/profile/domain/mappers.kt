package com.neqabty.recruitment.modules.profile.domain

import com.neqabty.recruitment.modules.profile.data.model.engineerdata.*
import com.neqabty.recruitment.modules.profile.domain.entity.engineerdata.*


fun CourseModel.toCourse(): CourseEntity {
    return CourseEntity(
        id = id,
        name = name
    )
}

fun DepartmentModel.toDepartment(): DepartmentEntity {
    return DepartmentEntity(
        id = id,
        name = name
    )
}

fun EducationModel.toEducationEntity(): EducationEntity{
    return EducationEntity(
        certificate = certificate,
        degree = degree,
        endDate = endDate,
        id = id,
        presentFlag = presentFlag,
        startDate = startDate
    )
}

fun ExperienceModel.toExperienceEntity(): ExperienceEntity{
    return ExperienceEntity(
        companyName = companyName,
        endDate = endDate,
        id = id,
        jobDescription = jobDescription,
        jobEmploymentType = jobEmploymentType,
        jobTitle = jobTitle,
        presentFlag = presentFlag,
        startDate = startDate
    )
}

 fun GovernorateModel.toGovernment(): GovernorateEntity {
    return GovernorateEntity(
        id = id,
        name = name
    )
}

 fun LanguageModel.toLanguage(): LanguageEntity {
    return LanguageEntity(
        id = id,
        name = name
    )
}

 fun NationalityModel.toNationality(): NationalityEntity {
    return NationalityEntity(
        id = id,
        name = name
    )
}

 fun MilitaryStatusModel.toMilitaryStatus(): MilitaryStatusEntity {
    return MilitaryStatusEntity(
        id = id,
        name = name
    )
}

 fun MaritalStatusModel.toMaritalStatus(): MaritalStatusEntity {
    return MaritalStatusEntity(
        id = id,
        name = name
    )
}

 fun SkillModel.toSkills(): SkillEntity {
    return SkillEntity(
        id = id,
        name = name,
        type = type
    )
}

 fun ZoneModel.toArea(): ZoneEntity {
    return ZoneEntity(
        id = id,
        name = name
    )
}

 fun UniversityModel.toUniversity(): UniversityEntity {
    return UniversityEntity(
        id = id,
        name = name
    )
}