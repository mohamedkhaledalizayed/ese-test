package com.neqabty.healthcare.auth.signup.presentation.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.isDigitsOnly
import com.google.gson.Gson

import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.signup.data.model.NeqabtySignupBody
import com.neqabty.healthcare.auth.signup.data.model.SignupBody
import com.neqabty.healthcare.auth.signup.data.model.SignupTogareenBody
import com.neqabty.healthcare.auth.signup.domain.entity.SignupParams
import com.neqabty.healthcare.auth.signup.domain.entity.syndicate.SyndicateListEntity
import com.neqabty.healthcare.core.data.Constants.TOGAREEN_CODE
import com.neqabty.healthcare.core.data.Constants.isSyndicateMember
import com.neqabty.healthcare.core.data.Constants.selectedSyndicateCode
import com.neqabty.healthcare.core.data.Constants.selectedSyndicatePosition
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.ErrorBody
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.core.utils.isValidEmail
import com.neqabty.healthcare.databinding.ActivitySignupBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>() {
    private lateinit var dialog: AlertDialog
    private val signupViewModel: SignupViewModel by viewModels()
    private val mSyndicatesAdapter = SyndicatesAdapter()
    private var syndicateListEntity: List<SyndicateListEntity>? = null
    private var syndicateCode = ""
    override fun getViewBinding() = ActivitySignupBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar(titleResId = R.string.signup)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(resources.getString(R.string.please_wait))
            .build()

        binding.phone.customSelectionActionModeCallback = actionMode
        binding.email.customSelectionActionModeCallback = actionMode
        binding.nationalId.customSelectionActionModeCallback = actionMode
        binding.serialNumber.customSelectionActionModeCallback = actionMode
        binding.membershipId.customSelectionActionModeCallback = actionMode
        binding.fullName.customSelectionActionModeCallback = actionMode
        binding.password.customSelectionActionModeCallback = actionMode

        if (isSyndicateMember){
            signupViewModel.getSyndicateList()
            binding.spinnerContainer.visibility = View.VISIBLE
            binding.nationalIdContainer.visibility = View.VISIBLE
            binding.serialNumberContainer.visibility = View.GONE
            binding.membershipIdContainer.visibility = View.VISIBLE

            binding.fullNameContainer.visibility = View.GONE
            binding.passwordContainer.visibility = View.GONE
        }else{
            binding.spinnerContainer.visibility = View.GONE
            binding.nationalIdContainer.visibility = View.GONE
            binding.serialNumberContainer.visibility = View.GONE
            binding.membershipIdContainer.visibility = View.GONE


            binding.fullNameContainer.visibility = View.VISIBLE
            binding.passwordContainer.visibility = View.VISIBLE

        }

        if (selectedSyndicateCode == TOGAREEN_CODE){
            binding.nationalIdContainer.visibility = View.GONE
            binding.serialNumberContainer.visibility = View.VISIBLE
        }

        binding.phone.setText(sharedPreferences.mobile)
        binding.phone.isEnabled = false
        binding.spSyndicates.adapter = mSyndicatesAdapter
        binding.spSyndicates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (syndicateListEntity != null && i != 0) {
                    syndicateCode = syndicateListEntity?.get(i - 1)!!.code
                    if (syndicateCode == TOGAREEN_CODE){
                        binding.nationalIdContainer.visibility = View.GONE
                        binding.serialNumberContainer.visibility = View.VISIBLE
                    }else{
                        binding.nationalIdContainer.visibility = View.VISIBLE
                        binding.serialNumberContainer.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}

        }

        signupViewModel.user.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.mobile.isNotEmpty()){
                            sharedPreferences.isSyndicateMember = true
                            sharedPreferences.isAuthenticated = true
                            sharedPreferences.mobile = binding.phone.text.toString()
                            sharedPreferences.token = resource.data.token.key
                            sharedPreferences.email = binding.email.text.toString()
                            sharedPreferences.name = resource.data.fullname ?: ""
                            sharedPreferences.nationalId = resource.data.nationalId ?: ""
                            sharedPreferences.membershipId = binding.membershipId.text.toString()
                            sharedPreferences.code = resource.data.entity.code
                            sharedPreferences.syndicateName = resource.data.entity.name
                            sharedPreferences.image = resource.data.entity.imageUrl ?: ""
                            if (syndicateCode == TOGAREEN_CODE){
                                confirmMessage(resources.getString(R.string.confirm_message_serial))
                            }else{
                                confirmMessage(resources.getString(R.string.confirm_message))
                            }
                        }else{
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        try {
                            val error = Gson().fromJson(resource.message.toString(), ErrorBody::class.java)
                            Toast.makeText(this, error.error, Toast.LENGTH_LONG).show()
                        }catch (e: Exception){
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

        }

        signupViewModel.neqabtyMember.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.mobile.isNotEmpty()){
                            sharedPreferences.isAuthenticated = true
                            sharedPreferences.token = resource.data!!.token
                            sharedPreferences.mobile = binding.phone.text.toString()
                            sharedPreferences.name = resource.data!!.fullname ?: ""
                            sharedPreferences.nationalId = resource.data!!.nationalId ?: ""
                            sharedPreferences.code = resource.data!!.entityCode
                            sharedPreferences.email = binding.email.text.toString()
                            sharedPreferences.syndicateName = resource.data!!.entityName
                            sharedPreferences.image = resource.data!!.entityImage ?: ""
                            finish()
                        }else{
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }

        }

        signupViewModel.syndicateList.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.isNotEmpty()){
                            syndicateListEntity = resource.data
                            mSyndicatesAdapter.submitList(
                                resource.data!!.toMutableList()
                                    .also { list -> list.add(0, SyndicateListEntity("",0,"", resources.getString(R.string.select_syndicates))) })
                            if (isSyndicateMember){
                                binding.spSyndicates.setSelection(selectedSyndicatePosition)
                                syndicateCode = selectedSyndicateCode
                            }else{
                                isSyndicateMember = true
                            }
                        }else{
                            Toast.makeText(this, resources.getString(R.string.there_is_no_syndicates), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }

        }
    }

    private fun confirmMessage(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(resources.getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.ok_btn)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()

    }

    fun signUp(view: View) {

        if (binding.phone.text.toString().isEmpty()){
            Toast.makeText(this, resources.getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
            return
        }

//        if(!binding.phone.text.toString().isMobileValid()) {
//            Toast.makeText(this, resources.getString(R.string.enter_correct_phone), Toast.LENGTH_LONG).show()
//            return
//        }

        if (binding.email.text.toString().isNullOrEmpty()){
            Toast.makeText(this, resources.getString(R.string.enter_email), Toast.LENGTH_LONG).show()
            return
        }

        if (!binding.email.text.toString().isValidEmail()){
            Toast.makeText(this, resources.getString(R.string.enter_correct_email), Toast.LENGTH_LONG).show()
            return
        }

        if (isSyndicateMember){

            if (binding.spSyndicates.selectedItemPosition == 0){
                Toast.makeText(this, resources.getString(R.string.select_syndicates), Toast.LENGTH_LONG).show()
                return
            }

            if (binding.membershipId.text.toString().isEmpty()){
                Toast.makeText(this, resources.getString(R.string.enter_membership_id), Toast.LENGTH_LONG).show()
                return
            }

            if (syndicateCode == TOGAREEN_CODE){
                if (binding.serialNumber.text.toString().isEmpty()){
                    Toast.makeText(this, "Enter Serial Number.", Toast.LENGTH_LONG).show()
                    return
                }
                signupViewModel.signup(
                    SignupTogareenBody(
                        entityCode = syndicateCode,
                        membershipId = binding.membershipId.text.toString(),
                        mobile = binding.phone.text.toString(),
                        serialNumber = binding.serialNumber.text.toString(),
                        email = binding.email.text.toString()
                    )
                )
            }else{
                if (binding.nationalId.text.toString().isEmpty() || !binding.nationalId.text.isDigitsOnly() || binding.nationalId.text.toString().length < 14){
                    Toast.makeText(this, resources.getString(R.string.enter_national_id), Toast.LENGTH_LONG).show()
                    return
                }

                signupViewModel.signup(
                    SignupBody(
                        entityCode = syndicateCode,
                        membershipId = binding.membershipId.text.toString(),
                        mobile = binding.phone.text.toString(),
                        nationalId = binding.nationalId.text.toString(),
                        email = binding.email.text.toString()
                    )
                )
            }
        }else{

            if (binding.fullName.text.toString().isEmpty()){
                Toast.makeText(this, resources.getString(R.string.enter_name), Toast.LENGTH_LONG).show()
                return
            }

            if (binding.password.text.toString().isEmpty()){
                Toast.makeText(this, resources.getString(R.string.enter_password), Toast.LENGTH_LONG).show()
                return
            }

            signupViewModel.signupNeqabtyMember(
                NeqabtySignupBody(
                    email = binding.email.text.toString(),
                    fullname = binding.fullName.text.toString(),
                    mobile = binding.phone.text.toString(),
                    password = binding.password.text.toString()
                )
            )
        }

    }

}