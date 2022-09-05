package com.neqabty.recruitment.modules.profile.view


import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.core.utils.Status
import com.neqabty.recruitment.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        profileViewModel.countries.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.governments.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.areas.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.universities.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.departments.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.languages.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.courses.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.companies.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.skills.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.grades.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.nationalities.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.militaryStatus.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.maritalStatus.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

        profileViewModel.industries.observe(this){
            it.let { resource ->
                when (resource.status){
                    Status.LOADING-> {

                    }
                    Status.SUCCESS -> {

                    }
                    Status.ERROR ->{

                    }
                }
            }
        }

//        profileViewModel.engineer.observe(this){
////            it.let { resource ->
////                when (resource.status){
////                    Status.LOADING-> {
////
////                    }
////                    Status.SUCCESS -> {
////
////                    }
////                    Status.ERROR ->{
////
////                    }
////                }
////            }
//        }
    }

}