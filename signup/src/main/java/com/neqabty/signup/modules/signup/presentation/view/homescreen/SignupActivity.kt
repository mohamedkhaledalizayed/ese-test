package com.neqabty.signup.modules.signup.presentation.view.homescreen

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.isDigitsOnly
import com.neqabty.core.data.Constants
import com.neqabty.core.data.Constants.isSyndicateMember
import com.neqabty.core.data.Constants.selectedSyndicateCode
import com.neqabty.core.data.Constants.selectedSyndicatePosition
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.core.utils.isMobileValid
import com.neqabty.signup.R
import com.neqabty.core.utils.isValidEmail
import com.neqabty.signup.databinding.ActivitySignupBinding
import com.neqabty.signup.modules.signup.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.signup.domain.entity.SignupParams
import com.neqabty.signup.modules.signup.domain.entity.syndicate.SyndicateListEntity
import com.neqabty.signup.modules.verifyphonenumber.view.VerifyPhoneActivity
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

        if (isSyndicateMember){
            binding.checkMember.visibility = View.GONE
        }

        binding.phone.setText(sharedPreferences.mobile)
        binding.phone.isEnabled = false
        binding.spSyndicates.adapter = mSyndicatesAdapter
        binding.spSyndicates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (syndicateListEntity != null && i != 0) {
                    syndicateCode = syndicateListEntity?.get(i - 1)!!.code
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}

        }

        binding.syndicateMember.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                isSyndicateMember = true
                binding.fullNameContainer.visibility = View.GONE
                binding.passwordContainer.visibility = View.GONE

                binding.spinnerContainer.visibility = View.VISIBLE
                binding.membershipIdContainer.visibility = View.VISIBLE
            }
        }

        binding.neqabtyMember.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                isSyndicateMember = false
                binding.fullNameContainer.visibility = View.VISIBLE
                binding.passwordContainer.visibility = View.VISIBLE

                binding.spinnerContainer.visibility = View.GONE
                binding.membershipIdContainer.visibility = View.GONE
            }
        }


        signupViewModel.user.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.nationalId.isNotEmpty()){
                            sharedPreferences.isSyndicateMember = true
                            sharedPreferences.isAuthenticated = true
                            sharedPreferences.mobile = binding.phone.text.toString()
                            sharedPreferences.token = resource.data!!.token
                            sharedPreferences.email = binding.email.text.toString()
                            sharedPreferences.name = resource.data!!.fullname ?: ""
                            sharedPreferences.nationalId = resource.data!!.nationalId
                            sharedPreferences.membershipId = binding.membershipId.text.toString()
                            sharedPreferences.code = resource.data!!.entityCode
                            sharedPreferences.syndicateName = resource.data!!.entityName
                            sharedPreferences.image = resource.data!!.entityImage
                            confirmMessage(resources.getString(R.string.confirm_message))
                        }else{
                            Toast.makeText(this, resources.getString(R.string.something_wrong), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
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
                        if (resource.data!!.nationalId.isNotEmpty()){
                            sharedPreferences.isAuthenticated = true
                            sharedPreferences.token = resource.data!!.token
                            sharedPreferences.mobile = binding.phone.text.toString()
                            sharedPreferences.name = resource.data!!.fullname ?: ""
                            sharedPreferences.nationalId = resource.data!!.nationalId
                            sharedPreferences.code = resource.data!!.entityCode
                            sharedPreferences.email = binding.email.text.toString()
                            sharedPreferences.syndicateName = resource.data!!.entityName
                            sharedPreferences.image = resource.data!!.entityImage
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

        signupViewModel.getSyndicateList()
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

        if (binding.nationalId.text.toString().isEmpty() || !binding.nationalId.text.isDigitsOnly() || binding.nationalId.text.toString().length < 14){
            Toast.makeText(this, resources.getString(R.string.enter_national_id), Toast.LENGTH_LONG).show()
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

            signupViewModel.signup(
                SignupParams(
                    entityCode = syndicateCode,
                    membershipId = binding.membershipId.text.toString(),
                    mobile = binding.phone.text.toString(),
                    national_id = binding.nationalId.text.toString(),
                    email = binding.email.text.toString()
                )
            )
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
                    nationalId = binding.nationalId.text.toString(),
                    password = binding.password.text.toString()
                )
            )
        }

    }

}