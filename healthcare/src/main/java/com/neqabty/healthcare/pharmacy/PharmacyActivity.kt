package com.neqabty.healthcare.pharmacy


import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.chefaa.home.view.ChefaaHomeActivity
import com.neqabty.healthcare.clinido.view.ClinidoActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPharmacyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PharmacyActivity : BaseActivity<ActivityPharmacyBinding>(), IPharmacySelection {

    private var clickedItem = ""
    override fun getViewBinding() = ActivityPharmacyBinding.inflate(layoutInflater)
    private lateinit var bottomSheetFragment: PharmacyTermsBottomSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.circularProgress.setProgress(60.0, 100.0)
        binding.backBtnHeader.setOnClickListener { finish() }
        binding.backBtn.setOnClickListener { finish() }
        binding.clinido.setOnClickListener {
            clickedItem = "clinido"
            bottomSheetFragment = PharmacyTermsBottomSheet.newInstance(clickedItem)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
        binding.chefaa.setOnClickListener {
            clickedItem = "chefaa"
            bottomSheetFragment = PharmacyTermsBottomSheet.newInstance(clickedItem)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }

    override fun onAgreeClicked() {
        if (clickedItem == "clinido") {
            val intent = Intent(this, ClinidoActivity::class.java)
            intent.putExtra("title", "pharmacy")
            startActivity(intent)
        } else {
            val intent = Intent(this, ChefaaHomeActivity::class.java)
            intent.putExtra("user_number", sharedPreferences.mobile)
            intent.putExtra("mobile_number", sharedPreferences.mobile)
            intent.putExtra("country_code", sharedPreferences.mobile.substring(0, 2))
            intent.putExtra("national_id", sharedPreferences.nationalId)
            intent.putExtra("name", sharedPreferences.name)
            intent.putExtra("jwt", "")
            startActivity(intent)

        }
    }
}