package com.neqabty.healthcare.core.more.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.verifyuser.view.VerifyUserActivity
import com.neqabty.healthcare.commen.clinido.view.ClinidoActivity
import com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.commen.settings.SettingsActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.commen.packages.packageslist.view.PackagesActivity
import com.neqabty.healthcare.core.syndicates.SyndicatesActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityMoreBinding
import com.neqabty.healthcare.mega.payment.view.selectservice.PaymentsActivity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.SearchResultActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoreActivity : BaseActivity<ActivityMoreBinding>() {
    private val moreViewModel: MoreViewModel by viewModels()
    override fun getViewBinding() = ActivityMoreBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(titleResId = R.string.menu_more)
        observeOnClinidoURL()
        initializeViews()
    }

    private fun initializeViews() {
        binding.bnvSyndicatesHome.selectedItemId = R.id.navigation_more

        binding.llMedicalNetwork.setOnClickListener {
            val intent = Intent(this, SearchResultActivity::class.java)
            startActivity(intent)
        }

        binding.llPackages.setOnClickListener {
            val intent = Intent(this, PackagesActivity::class.java)
            startActivity(intent)
        }

        binding.llHomeVisit.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
        }

        binding.llOnlineConsultation.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
        }

        binding.llDoctorsReservation.setOnClickListener {
            title = "doctors"
            moreViewModel.getUrl(phone = sharedPreferences.mobile, name = sharedPreferences.name, type = "doctors")
        }

        binding.llMedicine.setOnClickListener {
            openTermsDialog()
        }

        binding.llProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.llSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.ivProfileNav.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.bnvSyndicatesHome.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    finish()
                    true
                }
                R.id.navigation_syndicates -> {
                    val intent = Intent(this, SyndicatesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_payments -> {
                    val intent = Intent(this, PaymentsActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.navigation_more -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun observeOnClinidoURL() {
        moreViewModel.clinidoUrl.observe(this){
            when(it.status){
                Status.LOADING ->{
                    showProgressDialog()
                }
                Status.SUCCESS ->{
                    hideProgressDialog()
                    if (it.data!!.status){
                        val intent = Intent(this, ClinidoActivity::class.java)
                        intent.putExtra("url", it.data.url)
                        intent.putExtra("title", title)
                        startActivity(intent)
                    }else if (it.data.status_code == 405) {
                        Constants.mobileNumber = sharedPreferences.mobile
                        startActivity(Intent(this, VerifyUserActivity::class.java))
                        Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR ->{
                    hideProgressDialog()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //region
    override fun onResume() {
        super.onResume()

        binding.bnvSyndicatesHome.selectedItemId = R.id.navigation_more
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

            val intent = Intent(this, ClinidoActivity::class.java)
            intent.putExtra("url", Constants.MEDICINE_URL)
            intent.putExtra("title", "pharmacy")
            startActivity(intent)
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.disagree)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }
    //endregion
}