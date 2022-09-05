package com.neqabty.recruitment.modules.profile.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.recruitment.core.utils.AppUtils
import com.neqabty.recruitment.core.utils.Resource
import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import com.neqabty.recruitment.modules.profile.domain.entity.area.AreaEntity
import com.neqabty.recruitment.modules.profile.domain.entity.company.CompanyEntity
import com.neqabty.recruitment.modules.profile.domain.entity.country.CountryEntity
import com.neqabty.recruitment.modules.profile.domain.entity.courses.CourseEntity
import com.neqabty.recruitment.modules.profile.domain.entity.department.DepartmentEntity
import com.neqabty.recruitment.modules.profile.domain.entity.governement.GovernmentEntity
import com.neqabty.recruitment.modules.profile.domain.entity.grades.GradeEntity
import com.neqabty.recruitment.modules.profile.domain.entity.industries.IndustryEntity
import com.neqabty.recruitment.modules.profile.domain.entity.language.LanguageEntity
import com.neqabty.recruitment.modules.profile.domain.entity.maritalstatus.MaritalStatusEntity
import com.neqabty.recruitment.modules.profile.domain.entity.militarystatus.MilitaryStatusEntity
import com.neqabty.recruitment.modules.profile.domain.entity.nationalities.NationalityEntity
import com.neqabty.recruitment.modules.profile.domain.entity.skills.SkillsEntity
import com.neqabty.recruitment.modules.profile.domain.entity.university.UniversityEntity
import com.neqabty.recruitment.modules.profile.domain.usecase.ProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val profileUseCase: ProfileUseCase): ViewModel() {

    val countries = MutableLiveData<Resource<List<CountryEntity>>>()
    fun getCountries(){
        viewModelScope.launch(Dispatchers.IO){
            countries.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getCountries().collect(){
                    countries.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                countries.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val governments = MutableLiveData<Resource<List<GovernmentEntity>>>()
    fun getGovernments(){
        viewModelScope.launch(Dispatchers.IO){
            governments.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getGovernments().collect(){
                    governments.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                governments.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val areas = MutableLiveData<Resource<List<AreaEntity>>>()
    fun getAreas(){
        viewModelScope.launch(Dispatchers.IO){
            areas.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getAreas().collect(){
                    areas.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                areas.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val universities = MutableLiveData<Resource<List<UniversityEntity>>>()
    fun getUniversities(){
        viewModelScope.launch(Dispatchers.IO){
            universities.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getUniversities().collect(){
                    universities.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                universities.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val departments = MutableLiveData<Resource<List<DepartmentEntity>>>()
    fun getDepartments(){
        viewModelScope.launch(Dispatchers.IO){
            departments.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getDepartments().collect(){
                    departments.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                departments.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val languages = MutableLiveData<Resource<List<LanguageEntity>>>()
    fun getLanguages(){
        viewModelScope.launch(Dispatchers.IO){
            languages.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getLanguages().collect(){
                    languages.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                languages.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val courses = MutableLiveData<Resource<List<CourseEntity>>>()
    fun getCourses(){
        viewModelScope.launch(Dispatchers.IO){
            courses.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getCourses().collect(){
                    courses.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                courses.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val companies = MutableLiveData<Resource<List<CompanyEntity>>>()
    fun getCompanies(){
        viewModelScope.launch(Dispatchers.IO){
            companies.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getCompanies().collect(){
                    companies.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                companies.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val skills = MutableLiveData<Resource<List<SkillsEntity>>>()
    fun getSkills(){
        viewModelScope.launch(Dispatchers.IO){
            skills.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getSkills().collect(){
                    skills.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                skills.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val grades = MutableLiveData<Resource<List<GradeEntity>>>()
    fun getGrades(){
        viewModelScope.launch(Dispatchers.IO){
            grades.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getGrades().collect(){
                    grades.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                grades.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val nationalities = MutableLiveData<Resource<List<NationalityEntity>>>()
    fun getNationalities(){
        viewModelScope.launch(Dispatchers.IO){
            nationalities.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getNationalities().collect(){
                    nationalities.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                nationalities.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val militaryStatus = MutableLiveData<Resource<List<MilitaryStatusEntity>>>()
    fun getMilitaryStatus(){
        viewModelScope.launch(Dispatchers.IO){
            militaryStatus.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getMilitaryStatus().collect(){
                    militaryStatus.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                militaryStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val maritalStatus = MutableLiveData<Resource<List<MaritalStatusEntity>>>()
    fun getMaritalStatus(){
        viewModelScope.launch(Dispatchers.IO){
            maritalStatus.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getMaritalStatus().collect(){
                    maritalStatus.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                maritalStatus.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val industries = MutableLiveData<Resource<List<IndustryEntity>>>()
    fun getIndustries(){
        viewModelScope.launch(Dispatchers.IO){
            industries.postValue(Resource.loading(data = null))

            try {
                profileUseCase.getIndustries().collect(){
                    industries.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                industries.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }



    val course = MutableLiveData<Resource<String>>()
    fun getEngineerData(name: String){
        viewModelScope.launch(Dispatchers.IO){
            course.postValue(Resource.loading(data = null))

            try {
                profileUseCase.addCourse(name).collect(){
                    course.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                course.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val addSkills = MutableLiveData<Resource<String>>()
    fun addSkills(name: String, type: String){
        viewModelScope.launch(Dispatchers.IO){
            addSkills.postValue(Resource.loading(data = null))

            try {
                profileUseCase.addSkills(name, type).collect(){
                    addSkills.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                addSkills.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val university = MutableLiveData<Resource<String>>()
    fun addUniversity(name: String){
        viewModelScope.launch(Dispatchers.IO){
            university.postValue(Resource.loading(data = null))

            try {
                profileUseCase.addUniversity(name).collect(){
                    university.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                university.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val area = MutableLiveData<Resource<String>>()
    fun addArea(name: String){
        viewModelScope.launch(Dispatchers.IO){
            area.postValue(Resource.loading(data = null))

            try {
                profileUseCase.addArea(name).collect{
                    area.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                area.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val language = MutableLiveData<Resource<String>>()
    fun addLanguage(name: String){
        viewModelScope.launch(Dispatchers.IO){
            language.postValue(Resource.loading(data = null))

            try {
                profileUseCase.addLanguage(name).collect{
                    language.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                language.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }

    val engineerLanguage = MutableLiveData<Resource<String>>()
    fun addEngineerLanguage(engineer: Int, language: Int, level: String){
        viewModelScope.launch(Dispatchers.IO){
            engineerLanguage.postValue(Resource.loading(data = null))

            try {
                profileUseCase.addEngineerLanguage(engineer, language, level).collect{
                    engineerLanguage.postValue(Resource.success(data = it))
                }
            }catch (t: Throwable){
                engineerLanguage.postValue(Resource.error(data = null, message = AppUtils().handleError(t)))
            }
        }
    }
}