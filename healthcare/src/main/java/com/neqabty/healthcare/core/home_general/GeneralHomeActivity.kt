package com.neqabty.healthcare.core.home_general

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.ads.domain.entity.AdEntity
import com.neqabty.healthcare.chefaa.verifyuser.view.VerifyUserActivity
import com.neqabty.healthcare.clinido.view.ClinidoActivity
import com.neqabty.healthcare.contact.contact_providers.view.ContactProvidersActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.more.view.MoreActivity
import com.neqabty.healthcare.core.scanqrcode.ScanQrcodeScreen
import com.neqabty.healthcare.core.syndicates.SyndicatesActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityHomeGeneralSyndicateBinding
import com.neqabty.healthcare.invoices.view.InvoicesActivity
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.GovernorateEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.medicalnetwork.presentation.view.providerdetails.ProviderDetailsActivity
import com.neqabty.healthcare.medicalnetwork.presentation.view.searchresult.SearchResultActivity
import com.neqabty.healthcare.notification.NotificationsActivity
import com.neqabty.healthcare.packages.packageslist.view.PackagesActivity
import com.neqabty.healthcare.pharmacy.PharmacyActivity
import com.neqabty.healthcare.profile.view.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.json.JSONObject


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
        observeOnClinidoURL()
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
            generalHomeViewModel.getUrl(phone = sharedPreferences.mobile, name = sharedPreferences.name, type = "doctors")
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
                    val intent = Intent(this, InvoicesActivity::class.java)
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
        if (sharedPreferences.isContactActiveSubscriber) {
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
        binding.ivContactServiceProviders.setOnClickListener {
            startActivity(Intent(this, ContactProvidersActivity::class.java))
        }
        binding.ivContactSubscribe.setOnClickListener {
            sharedPreferences.isSkippedToHome = false
            startActivity(Intent(this, getTheNextActivityFromIntro()))
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
                            sharedPreferences.isContactActiveSubscriber = !resource.data.authorized
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

    private fun observeOnClinidoURL() {
        generalHomeViewModel.clinidoUrl.observe(this){
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

    override fun onBackPressed() {
        closeApp()
    }

    //region
    override fun onResume() {
        super.onResume()

        binding.bnvSyndicatesHome.selectedItemId = R.id.navigation_home
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            QR_CODE_REQUEST -> {
                if (resultCode == RESULT_OK) {
                    try {
                        val data = data?.getStringExtra("data")
                        val dataObject = JSONObject(data)

                        val id = dataObject.getString("medical_provider_id")
                        val name = dataObject.getString("name")
                        val address = dataObject.getString("address")
                        val mobile = dataObject.getString("mobile")
                        val email = dataObject.getString("email")
                        val branchCode = dataObject.getString("branch_code")
                        val branchId = dataObject.getString("branch_id")

                        Log.d("QR", data!!)

                        val providersEntity = ProvidersEntity(address = address, email = email, id = id.toInt(), name = name, hasQR = true, mobile = mobile, area = null, degree = null , image = null, governorate = GovernorateEntity("", 0), notes = "", phone = mobile, price = null, profession = null, serviceProviderType = null)
                        val intent = Intent(this@GeneralHomeActivity, ProviderDetailsActivity::class.java)
                        intent.putExtra("provider", providersEntity)
                        startActivity(intent)
                    }catch (e: Exception){
                        Toast.makeText(this, "Wrong Qr Code", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    //endregion
}