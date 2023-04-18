package com.neqabty.healthcare.core.home_general

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.chefaa.modules.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.clinido.view.ClinidoActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.more.view.MoreActivity
import com.neqabty.healthcare.core.syndicates.SyndicatesActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityHomeGeneralSyndicateBinding
import com.neqabty.healthcare.mega.payment.view.selectservice.PaymentsActivity
import com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.core.packages.PackagesActivity
import com.neqabty.healthcare.sustainablehealth.search.presentation.view.searchresult.SearchResultActivity
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.*


@AndroidEntryPoint
class GeneralHomeActivity : BaseActivity<ActivityHomeGeneralSyndicateBinding>() {
    private val generalHomeViewModel: GeneralHomeViewModel by viewModels()
    private val listAds = ArrayList<AdEntity>()
    private var title = ""
    private val list = mutableListOf<CarouselItem>()
    override fun getViewBinding() = ActivityHomeGeneralSyndicateBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(title = "", show = false)
        initializeViews()
    }

    private fun initializeViews() {
        if (sharedPreferences.name.isNotBlank()) {
            binding.tvName.text = sharedPreferences.name
        } else if (sharedPreferences.mobile.isNotBlank()) {
            binding.tvName.text = sharedPreferences.mobile
        }

        binding.icBanners.registerLifecycle(lifecycle)

        generalHomeViewModel.getAds()
        generalHomeViewModel.ads.observe(this) {
            listAds.addAll(it)
            for (data: AdEntity in it) {
                list.add(
                    CarouselItem(
                        imageUrl = data.image,
                        caption = ""
                    )
                )
            }

            binding.icBanners.setData(list)
        }

        binding.llPharmacy.setOnClickListener {
            val intent = Intent(this, ChefaaHomeActivity::class.java)
            intent.putExtra("user_number", sharedPreferences.mobile)
            intent.putExtra("mobile_number", sharedPreferences.mobile)
            intent.putExtra("country_code", sharedPreferences.mobile.substring(0, 2))
            intent.putExtra("national_id", sharedPreferences.nationalId)
            intent.putExtra("name", sharedPreferences.name)
            intent.putExtra("jwt", "")
            startActivity(intent)
        }

        binding.llDoctorsReservation.setOnClickListener {
            title = "doctors"
            generalHomeViewModel.getUrl(phone = sharedPreferences.mobile, type = "doctors")
        }

        binding.llOnlineConsultation.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
        }

        binding.ivSeha.setOnClickListener {
            val intent = Intent(this, PackagesActivity::class.java)
            startActivity(intent)
        }

        binding.ivMedicalNetworkBg.setOnClickListener {
            val intent = Intent(this, SearchResultActivity::class.java)
            startActivity(intent)
        }

        binding.ivProfileNav.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        generalHomeViewModel.clinidoUrl.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgressDialog()
                }
                Status.SUCCESS -> {
                    hideProgressDialog()
                    if (it.data!!.status) {
                        val intent = Intent(this, ClinidoActivity::class.java)
                        intent.putExtra("url", it.data.url)
                        intent.putExtra("title", title)
                        if (title == "doctors")
                            Constants.DOCTORS_RESERVATION_URL = it.data.url
                        else
                            Constants.MEDICINE_URL = it.data.url
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.bnvSyndicatesHome.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_syndicates -> {
                    val intent = Intent(this, SyndicatesActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_payments -> {
                    val intent = Intent(this, PaymentsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_more -> {
                    val intent = Intent(this, MoreActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember) {
            closeApp(getString(R.string.exit))
        } else {
            finish()
        }
    }

    private fun closeApp(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            finishAffinity()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    //region
    override fun onResume() {
        super.onResume()

        binding.bnvSyndicatesHome.selectedItemId = R.id.navigation_home
    }
    //endregion
}