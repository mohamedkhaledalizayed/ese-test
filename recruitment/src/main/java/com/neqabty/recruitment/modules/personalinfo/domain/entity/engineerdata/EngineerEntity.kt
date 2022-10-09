package com.neqabty.recruitment.modules.personalinfo.domain.entity.engineerdata



data class EngineerEntity(
    val address: String,
//    val city: CityEntity,
    val courses: List<CourseEntity>,
    val dateOfBirth: String,
    val department: DepartmentEntity,
    val educations: List<EducationEntity>,
    val email: String,
    val experiences: List<ExperienceEntity>,
    val gender: String,
    val governorate: GovernorateEntity,
    val grade: String?,
    val graduationYear: Int,
    val languages: List<LanguageEntity>,
    val linkedInLink: String,
    val maritalStatus: MaritalStatusEntity,
    val membershipId: String,
    val membershipIdFile: String?,
    val militaryFile: String?,
    val militaryStatus: MilitaryStatusEntity,
    val mobile: String,
    val name: String,
    val nationalId: String,
    val nationalIdFile: String?,
    val nationality: NationalityEntity,
    val phone: String,
    val skills: List<SkillEntity>,
    val university: UniversityEntity,
    val workingStatus: String,
    val yearsOfExperience: Int,
    val zone: ZoneEntity
)