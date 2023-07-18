package com.neqabty.healthcare.core.home_syndicates.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.healthcare.chefaa.verifyuser.view.VerifyUserActivity
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.clinido.view.ClinidoActivity
import com.neqabty.healthcare.commen.contact_providers.view.ContactProvidersActivity
import com.neqabty.healthcare.commen.invoices.view.InvoicesActivity
import com.neqabty.healthcare.commen.notification.NotificationsActivity
import com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.commen.syndicateservices.domain.entity.SyndicateServiceEntity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.more.view.MoreActivity
import com.neqabty.healthcare.commen.packages.packageslist.view.PackagesActivity
import com.neqabty.healthcare.commen.pharmacy.PharmacyActivity
import com.neqabty.healthcare.core.scanqrcode.ScanQrcodeScreen
import com.neqabty.healthcare.core.syndicates.SyndicatesActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityHomeSyndicateBinding
import com.neqabty.healthcare.mega.payment.view.selectservice.ServicesActivity
import com.neqabty.healthcare.news.view.newslist.NewsListActivity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.GovernorateEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.providerdetails.ProviderDetailsActivity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.SearchResultActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.json.JSONObject


@AndroidEntryPoint
class SyndicatesHomeActivity : BaseActivity<ActivityHomeSyndicateBinding>() {
    private val syndicatesHomeViewModel: SyndicatesHomeViewModel by viewModels()
    private val listAds = ArrayList<AdEntity>()
    private var title = ""
    private val list = mutableListOf<CarouselItem>()
    private val mAdapter = SyndicateServicesAdapter()
    private val QR_CODE_REQUEST = 201
    override fun getViewBinding() = ActivityHomeSyndicateBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(title = "", show = false)
        observeOnCheckMemberStatus()
        getContactMemberStatus()
        observeOnClinidoURL()
        observeOnSyndicateServices()
        getSyndicateServices()
        initializeViews()
    }

    private fun initializeViews() {
        if(!sharedPreferences.image.isNullOrBlank())
            Picasso.get().load(sharedPreferences.image).placeholder(R.drawable.logo).into(binding.ivSyndicateLogo)

        binding.ivNotification.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }

        renderContactCard()

        binding.tvWelcomeIn.text = getString(R.string.welcome_in, sharedPreferences.name)
        binding.tvSyndicateName.text = sharedPreferences.syndicateName

        binding.icBanners.registerLifecycle(lifecycle)

//        syndicatesHomeViewModel.getAds()
        syndicatesHomeViewModel.ads.observe(this) {
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
            syndicatesHomeViewModel.getUrl(phone = sharedPreferences.mobile, type = "doctors", name = sharedPreferences.name)
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

        syndicatesHomeViewModel.clinidoUrl.observe(this) {
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
        binding.ivContactServiceProviders.setOnClickListener {
            startActivity(Intent(this, ContactProvidersActivity::class.java))
        }
        binding.ivContactSubscribe.setOnClickListener {
            startActivity(Intent(this, getContactEntryPoint()))
        }
    }

    private fun getContactMemberStatus() {
        syndicatesHomeViewModel.checkMemberStatus(nationalId = sharedPreferences.nationalId)
    }

    private fun observeOnCheckMemberStatus() {
        syndicatesHomeViewModel.checkMemberStatus.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        binding.root.visibility = View.VISIBLE
                        if (resource.data != null) {
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

    private fun observeOnClinidoURL() {
        syndicatesHomeViewModel.clinidoUrl.observe(this){
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

    private fun getSyndicateServices() {
        syndicatesHomeViewModel.getSyndicateServices(sharedPreferences.code, "")
    }

    private fun observeOnSyndicateServices() {
        syndicatesHomeViewModel.syndicateServices.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null) {
                            binding.tvSyndicateServices.visibility = View.VISIBLE
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                    }
                }
            }
        }

        mAdapter.onItemClickListener = object :
            SyndicateServicesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: SyndicateServiceEntity) {
                when (item.serviceCategory?.name) {
                    "Subscriptions" -> {
                        val intent = Intent(this@SyndicatesHomeActivity, ServicesActivity::class.java)
                        intent.putExtra("id", item.code)
                        intent.putExtra("name", item.name)
                        startActivity(intent)
                    }
                    "News" -> {
                        val intent = Intent(this@SyndicatesHomeActivity, NewsListActivity::class.java)
                        startActivity(intent)
                    }
                    else -> {

                    }
                }
            }
        }
        binding.rvSyndicateServices.adapter = mAdapter
    }

    override fun onBackPressed() {
        if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember) {
            closeApp(getString(R.string.exit))
        } else {
            finish()
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
            title = "pharmacy"
            syndicatesHomeViewModel.getUrl(phone = sharedPreferences.mobile, type = "pharmacy", name = sharedPreferences.name)
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.disagree)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

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
                        val intent = Intent(this@SyndicatesHomeActivity, ProviderDetailsActivity::class.java)
                        intent.putExtra("provider", providersEntity)
                        startActivity(intent)
                    }catch (e: Exception){
                        Toast.makeText(this, "Wrong Qr Code", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
    //region
    override fun onResume() {
        super.onResume()

        binding.bnvSyndicatesHome.selectedItemId = R.id.navigation_home
    }
    //endregion
}