package com.neqabty.signup.modules.signup.presentation.view.homescreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.isDigitsOnly
import com.neqabty.signup.R
import com.neqabty.signup.core.ui.BaseActivity
import com.neqabty.signup.core.utils.Status
import com.neqabty.signup.core.utils.isMobileValid
import com.neqabty.signup.core.utils.isValidEmail
import com.neqabty.signup.databinding.ActivitySignupBinding
import com.neqabty.signup.modules.signup.data.model.NeqabtySignupBody
import com.neqabty.signup.modules.signup.domain.entity.SignupParams
import com.neqabty.signup.modules.signup.domain.entity.syndicate.SyndicateListEntity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>() {
    private lateinit var dialog: AlertDialog
    private val signupViewModel: SignupViewModel by viewModels()
    private val mSyndicatesAdapter = SyndicatesAdapter()
    private var syndicateListEntity: List<SyndicateListEntity>? = null
    private var syndicateCode = ""
    private var isSyndicateMember = true
    override fun getViewBinding() = ActivitySignupBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar(titleResId = R.string.signup)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("من فضلك انتظر...")
            .build()


        binding.phone.setText(intent.getStringExtra("phoneNumber"))
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
                            sharedPreferences.isPhoneVerified = true
                            sharedPreferences.isSyndicateMember = true
                            sharedPreferences.mobile = binding.phone.text.toString()
                            sharedPreferences.name = resource.data.fullname ?: ""
                            sharedPreferences.nationalId = resource.data.nationalId
                            sharedPreferences.code = resource.data.entityCode
                            finish()
                        }else{
                            Toast.makeText(this, "حدث خطاء", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
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
                            sharedPreferences.isPhoneVerified = true
                            sharedPreferences.mobile = binding.phone.text.toString()
                            sharedPreferences.name = resource.data.fullname ?: ""
                            sharedPreferences.nationalId = resource.data.nationalId
                            sharedPreferences.code = resource.data.entityCode
                            finish()
                        }else{
                            Toast.makeText(this, "حدث خطاء", Toast.LENGTH_LONG).show()
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
                                resource.data.toMutableList()
                                    .also { list -> list.add(0, SyndicateListEntity("",0,"", "اختر النقابة")) })
                        }else{
                            Toast.makeText(this, "لا يوجد نقابات", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }

        }
    }

    fun signUp(view: View) {

        if (binding.phone.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم الموبايل", Toast.LENGTH_LONG).show()
            return
        }

        if(!binding.phone.text.toString().isMobileValid()) {
            Toast.makeText(this, "من فضلك ادخل رقم صحيح", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.email.text.toString().isNullOrEmpty()){
            Toast.makeText(this, "من فضلك ادخل البريد الالكترونى", Toast.LENGTH_LONG).show()
            return
        }

        if (!binding.email.text.toString().isValidEmail()){
            Toast.makeText(this, "من فضلك ادخل البريد الالكترونى بشكل صحيح.", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.nationalId.text.toString().isEmpty() || !binding.nationalId.text.isDigitsOnly() || binding.nationalId.text.toString().length < 14){
            Toast.makeText(this, "من فضلك ادخل الرقم القومى بشكل صحيح", Toast.LENGTH_LONG).show()
            return
        }

        if (isSyndicateMember){

            if (binding.spSyndicates.selectedItemPosition == 0){
                Toast.makeText(this, "من فضلك اختر النقابة.", Toast.LENGTH_LONG).show()
                return
            }

            if (binding.membershipId.text.toString().isEmpty()){
                Toast.makeText(this, "من فضلك ادخل رقم العضوية", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this, "من فضلك ادخل الأسم.", Toast.LENGTH_LONG).show()
                return
            }

            if (binding.password.text.toString().isEmpty()){
                Toast.makeText(this, "من فضلك ادخل كلمة المرور.", Toast.LENGTH_LONG).show()
                return
            }

            signupViewModel.signupNeqabtyMember(
                NeqabtySignupBody(
                    email = binding.email.text.toString(),
                    fullname = binding.membershipId.text.toString(),
                    mobile = binding.phone.text.toString(),
                    nationalId = binding.nationalId.text.toString(),
                    password = binding.password.text.toString()
                )
            )
        }

    }

}