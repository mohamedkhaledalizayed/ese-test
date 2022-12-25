package com.neqabty.recruitment.modules.personalinfo.view


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.neqabty.recruitment.R
import com.neqabty.recruitment.core.ui.BaseActivity
import com.neqabty.recruitment.core.utils.Status
import com.neqabty.recruitment.databinding.ActivityPersonalInfoBinding
import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInfoActivity : BaseActivity<ActivityPersonalInfoBinding>() {
    private val nationalityAdapter = CustomAdapter()
    private val personalInfoViewModel: PersonalInfoViewModel by viewModels()
    private var editMode = false
    override fun getViewBinding() = ActivityPersonalInfoBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "البيانات الشخصية")

        saveChanges()

        binding.editInfo.setOnClickListener {
            editMode = !editMode
            editChanges()
        }
        binding.saveBtn.setOnClickListener {
            editMode = !editMode
            saveChanges()
        }

        personalInfoViewModel.getEngineerInfo("")
        personalInfoViewModel.engineer.observe(this){
            it.let {
                resource ->

                when(resource.status){
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.container.visibility = View.VISIBLE

                        binding.name.text = resource.data?.name
                        binding.phone.setText(resource.data?.phone)
                        binding.birthdate.text = resource.data?.dateOfBirth
                        binding.nationalId.setText(resource.data?.nationalId)
                        binding.membershipId.text = resource.data?.membershipId
                        binding.nationalityValue.text = resource.data?.nationality?.name
                        binding.maritalValue.text = resource.data?.maritalStatus?.name
                        binding.genderValue.text = resource.data?.gender

                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }

        personalInfoViewModel.getMaritalStatus()
        personalInfoViewModel.maritalStatus.observe(this){
            it.let {
                resource ->

                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        val mAdapter = CustomAdapter()
                        binding.spMarital.adapter = mAdapter
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }

        personalInfoViewModel.getNationalities()
        personalInfoViewModel.nationalities.observe(this){
            it.let {
                resource ->

                when(resource.status){
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {

                        binding.spNationality.adapter = nationalityAdapter
                        nationalityAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        Log.e("ERROR", resource.message.toString())
                    }
                }

            }
        }
    }

    private fun editChanges(){
        binding.phone.isEnabled = true
        binding.birthdate.isEnabled = true
        binding.nationalId.isEnabled = true


        binding.nationalityContainer.visibility = View.VISIBLE
        binding.nationalityValue.visibility = View.GONE

        binding.maritalContainer.visibility = View.VISIBLE
        binding.maritalValue.visibility = View.GONE

        binding.genderContainer.visibility = View.VISIBLE
        binding.genderValue.visibility = View.GONE

        binding.saveBtn.visibility = View.VISIBLE
        binding.addDateImage.visibility = View.VISIBLE

    }

    private fun saveChanges(){
        binding.phone.isEnabled = false
        binding.birthdate.isEnabled = false
        binding.nationalId.isEnabled = false


        binding.nationalityContainer.visibility = View.GONE
        binding.nationalityValue.visibility = View.VISIBLE

        binding.maritalContainer.visibility = View.GONE
        binding.maritalValue.visibility = View.VISIBLE

        binding.genderContainer.visibility = View.GONE
        binding.genderValue.visibility = View.VISIBLE

        binding.addDateImage.visibility = View.GONE

        if (binding.phone.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم الهاتف.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spNationality.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك اختر الجنسية.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spMarital.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك اختر الحالة الاجتماعية.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.spGender.selectedItemPosition == 0){
            Toast.makeText(this, "من فضلك اختر النوع.", Toast.LENGTH_LONG).show()
            return
        }

        val body = EngineerBody(
            address = "",
            dateOfBirth = "",
            email = "",
            gender = "",
            graduationYear = 0,
            linkedInLink = "",
            maritalStatus = 0,
            nationalId = "",
            phone = "",
            workingStatus = ""
        )

        personalInfoViewModel.updateEngineerInfo("", body)




    }
}