package com.neqabty.healthcare.pharmacy


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.home.view.ChefaaHomeActivity
import com.neqabty.healthcare.clinido.view.ClinidoActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPharmacyBinding
import com.neqabty.healthcare.onboarding.signup.view.SignupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PharmacyActivity : BaseActivity<ActivityPharmacyBinding>(), IPharmacySelection {

    private var clickedItem = ""
    override fun getViewBinding() = ActivityPharmacyBinding.inflate(layoutInflater)
    private val  bottomSheetFragment: PharmacyTermsBottomSheet by lazy { PharmacyTermsBottomSheet() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.circularProgress.setProgress(60.0, 100.0)
        binding.backBtnHeader.setOnClickListener { finish() }
        binding.backBtn.setOnClickListener { finish() }
        binding.clinido.setOnClickListener {
            clickedItem = "clinido"
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.chefaa.setOnClickListener {
            clickedItem = "chefaa"
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
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

    override fun onAgreeClicked() {
        if (clickedItem == "clinido"){
            val intent = Intent(this, ClinidoActivity::class.java)
            intent.putExtra("title", "pharmacy")
            startActivity(intent)
        }else{
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
    }
}