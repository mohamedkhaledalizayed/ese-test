package com.neqabty.healthcare.commen.pharmacy

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.signup.presentation.view.SignupActivity
import com.neqabty.healthcare.chefaa.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.healthcare.chefaa.verifyuser.view.VerifyUserActivity
import com.neqabty.healthcare.commen.clinido.view.ClinidoActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.mobileNumber
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPharmacyBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class PharmacyActivity : BaseActivity<ActivityPharmacyBinding>() {

    private lateinit var loading: AlertDialog
    private val pharmacyViewModel: PharmacyViewModel by viewModels()
    override fun getViewBinding() = ActivityPharmacyBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPharmacyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        binding.itemChefaa.setOnClickListener {
            if (sharedPreferences.isAuthenticated){
                val intent = Intent(this, ChefaaHomeActivity::class.java)
                intent.putExtra("user_number", sharedPreferences.mobile)
                intent.putExtra("mobile_number", sharedPreferences.mobile)
                intent.putExtra("country_code", sharedPreferences.mobile.substring(0,2))
                intent.putExtra("national_id", sharedPreferences.nationalId)
                intent.putExtra("name", sharedPreferences.name)
                intent.putExtra("jwt", "")
                startActivity(intent)
            }else{
                askForLogin("عفوا هذا الرقم غير مسجل من قبل، برجاء تسجيل الدخول.")
            }
        }

        binding.itemClinido.setOnClickListener {
            if (sharedPreferences.isAuthenticated) {
                openTermsDialog()
            } else {
                askForLogin("عفوا هذا الرقم غير مسجل من قبل، برجاء تسجيل الدخول.")
            }
        }

        pharmacyViewModel.clinidoUrl.observe(this){
            when(it.status){
                Status.LOADING ->{
                    loading.show()
                }
                Status.SUCCESS ->{
                    loading.dismiss()
                    if (it.data!!.status){
                        val intent = Intent(this, ClinidoActivity::class.java)
                        intent.putExtra("url", it.data.url)
                        intent.putExtra("title", title)
                        startActivity(intent)
                    }else if (it.data.status_code == 405) {
                        mobileNumber = sharedPreferences.mobile
                        startActivity(Intent(this, VerifyUserActivity::class.java))
                        Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR ->{
                    loading.dismiss()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun openTermsDialog() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("الشروط والاحكام")
        alertDialog.setMessage(resources.getString(R.string.terms))
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            pharmacyViewModel.getUrl(
                phone = sharedPreferences.mobile,
                type = "pharmacy",
                name = sharedPreferences.name,
                entityCode = sharedPreferences.code)
            title = "العلاج الشهرى"
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.disagree)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    private fun askForLogin(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            Constants.isSyndicateMember = false
            Constants.selectedSyndicateCode = ""
            Constants.selectedSyndicatePosition = 0
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }
}