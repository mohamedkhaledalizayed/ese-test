package com.neqabty.healthcare.commen.eselanding


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.neqabty.chefaa.modules.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.signup.presentation.view.SignupActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityEseLandingBinding
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.SehaHomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EseLandingActivity : BaseActivity<ActivityEseLandingBinding>() {

    override fun getViewBinding() = ActivityEseLandingBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


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

        binding.itemNeqabty.setOnClickListener {
                val intent = Intent(this, SehaHomeActivity::class.java)
                startActivity(intent)
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