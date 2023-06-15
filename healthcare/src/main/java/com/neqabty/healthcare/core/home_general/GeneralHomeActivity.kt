package com.neqabty.healthcare.core.home_general

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.notification.NotificationsActivity
import com.neqabty.healthcare.commen.pharmacy.PharmacyActivity
import com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.core.more.view.MoreActivity
import com.neqabty.healthcare.core.packages.PackagesActivity
import com.neqabty.healthcare.core.scanqrcode.ScanQrcodeScreen
import com.neqabty.healthcare.core.syndicates.SyndicatesActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityHomeGeneralSyndicateBinding
import com.neqabty.healthcare.mega.payment.view.selectservice.PaymentsActivity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.SearchResultActivity
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


@AndroidEntryPoint
class GeneralHomeActivity : BaseActivity<ActivityHomeGeneralSyndicateBinding>() {
    private val generalHomeViewModel: GeneralHomeViewModel by viewModels()
    private val listAds = ArrayList<AdEntity>()
    private var title = ""
    private val list = mutableListOf<CarouselItem>()
    private val QR_CODE_REQUEST = 201
    override fun getViewBinding() = ActivityHomeGeneralSyndicateBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(title = "", show = false)
        observeOnCheckMemberStatus()
        getContactMemberStatus()
        initializeViews()
    }

    private fun initializeViews() {
        if (sharedPreferences.name.isNotBlank()) {
            binding.tvName.text = sharedPreferences.name
        } else if (sharedPreferences.mobile.isNotBlank()) {
            binding.tvName.text = sharedPreferences.mobile
        }

        binding.ivNotification.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        renderContactCard()

        binding.ivContactSubscribe.setOnClickListener {
            startActivity(Intent(this, getContactEntryPoint()))
        }

        binding.icBanners.registerLifecycle(lifecycle)

//        generalHomeViewModel.getAds()
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
            val intent = Intent(this, PharmacyActivity::class.java)
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

    private fun renderContactCard() {
        if (sharedPreferences.isContactSubscriber) {
            binding.tvContactName.text = sharedPreferences.name
            binding.tvBalance.visibility = View.VISIBLE
            binding.ivContactQr.visibility = View.VISIBLE
            binding.ivContactServiceProviders.visibility = View.VISIBLE
            binding.ivContactSubscribe.visibility = View.GONE
        } else {
            binding.tvContactName.text = getString(R.string.contact_home_benefits)
            binding.tvBalance.visibility = View.GONE
            binding.ivContactQr.visibility = View.INVISIBLE
            binding.ivContactServiceProviders.visibility = View.INVISIBLE
            binding.ivContactSubscribe.visibility = View.VISIBLE
        }
        binding.ivContactQr.setOnClickListener {
            val intent = Intent(this, ScanQrcodeScreen::class.java)
            startActivityForResult(intent, QR_CODE_REQUEST)
        }
    }

    private fun getContactMemberStatus() {
        generalHomeViewModel.checkMemberStatus(nationalId = sharedPreferences.nationalId)
    }

    private fun observeOnCheckMemberStatus() {
        generalHomeViewModel.checkMemberStatus.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        binding.root.visibility = View.VISIBLE
                        if (resource.data != null){
                            sharedPreferences.isContactSubscriber = !resource.data.authorized
                            renderContactCard()
                        } else {
                            getContactMemberStatus()
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                    }
                }
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