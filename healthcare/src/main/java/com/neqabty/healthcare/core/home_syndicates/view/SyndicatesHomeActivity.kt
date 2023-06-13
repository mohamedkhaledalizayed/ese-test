package com.neqabty.healthcare.core.home_syndicates.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.common.util.Base64Utils.decode
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.healthcare.commen.ads.domain.entity.AdEntity
import com.neqabty.healthcare.commen.clinido.view.ClinidoActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.more.view.MoreActivity
import com.neqabty.healthcare.core.syndicates.SyndicatesActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityHomeSyndicateBinding
import com.neqabty.healthcare.mega.payment.view.selectservice.PaymentsActivity
import com.neqabty.healthcare.commen.profile.view.profile.ProfileActivity
import com.neqabty.healthcare.core.packages.PackagesActivity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.SearchResultActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.util.*


@AndroidEntryPoint
class SyndicatesHomeActivity : BaseActivity<ActivityHomeSyndicateBinding>() {
    private val syndicatesHomeViewModel: SyndicatesHomeViewModel by viewModels()
    private val listAds = ArrayList<AdEntity>()
    private var title = ""
    private val list = mutableListOf<CarouselItem>()
    override fun getViewBinding() = ActivityHomeSyndicateBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setupToolbar(title = "", show = false)
        observeOnCheckMemberStatus()
        getContactMemberStatus()
        initializeViews()
    }

    private fun initializeViews() {
        if(!sharedPreferences.image.isNullOrBlank())
            Picasso.get().load(sharedPreferences.image).placeholder(R.drawable.logo).into(binding.ivSyndicateLogo)

        renderContactCard()

        binding.ivContactSubscribe.setOnClickListener {
            startActivity(Intent(this, getContactEntryPoint()))
        }

        binding.tvWelcomeIn.text = getString(R.string.welcome_in, sharedPreferences.name)
        binding.tvSyndicateName.text = sharedPreferences.syndicateName

        binding.icBanners.registerLifecycle(lifecycle)

        syndicatesHomeViewModel.getAds()
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

        binding.llRenewSubscription.setOnClickListener {
            val intent = Intent(this, PaymentsActivity::class.java)
            startActivity(intent)
        }

        binding.llVisitHome.setOnClickListener {
            Toast.makeText(this, getString(R.string.service_unavailable), Toast.LENGTH_LONG).show()
        }

        binding.llMedicine.setOnClickListener {
            openTermsDialog()
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
            syndicatesHomeViewModel.getUrl(phone = sharedPreferences.mobile, type = "doctors")
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
                        if (resource.data != null){
                            sharedPreferences.isContactSubscriber = true
                            renderContactCard()
                        } else {
                            getContactMemberStatus()
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        getContactMemberStatus()
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
            syndicatesHomeViewModel.getUrl(phone = sharedPreferences.mobile, type = "pharmacy")
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

    //region
    override fun onResume() {
        super.onResume()

        binding.bnvSyndicatesHome.selectedItemId = R.id.navigation_home
    }
    //endregion
}