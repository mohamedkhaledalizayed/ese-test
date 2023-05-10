package com.neqabty.healthcare.commen.eselanding


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.signup.presentation.view.SignupActivity
import com.neqabty.healthcare.chefaa.verifyuser.view.VerifyUserActivity
import com.neqabty.healthcare.commen.clinido.view.ClinidoActivity
import com.neqabty.healthcare.commen.pharmacy.PharmacyActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.mobileNumber
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityEseLandingBinding
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.SehaHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class EseLandingActivity : BaseActivity<ActivityEseLandingBinding>() {

    private lateinit var loading: AlertDialog
    private val eseLandingViewModel: EseLandingViewModel by viewModels()
    override fun getViewBinding() = ActivityEseLandingBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        binding.ese.loop(true)
        binding.itemEse.setOnClickListener {
            val launchIntent = packageManager.getLaunchIntentForPackage("com.neqabty")
            if (launchIntent == null){
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.neqabty")
                    )
                )
            }else{
                startActivity(launchIntent)
            }
        }

        binding.itemPharmacy.setOnClickListener {
            val intent = Intent(this, PharmacyActivity::class.java)
            startActivity(intent)
        }

        eseLandingViewModel.clinidoUrl.observe(this){
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

        binding.itemNeqabty.setOnClickListener {
            val intent = Intent(this, SehaHomeActivity::class.java)
            startActivity(intent)
        }

        binding.itemDoctor.setOnClickListener {
            if (sharedPreferences.isAuthenticated) {
                eseLandingViewModel.getUrl(
                    phone = sharedPreferences.mobile,
                    type = "doctors",
                    name = sharedPreferences.name,
                    entityCode = sharedPreferences.code
                )
                title = "حجز أطباء"
            } else {
                askForLogin("عفوا هذا الرقم غير مسجل من قبل، برجاء تسجيل الدخول.")
            }
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
}
