package com.neqabty.healthcare.core.packages


import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPackageDetailsBinding
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view.SubscriptionActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PackageDetailsActivity : BaseActivity<ActivityPackageDetailsBinding>() {


    override fun getViewBinding() = ActivityPackageDetailsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val packageDetails = intent.extras?.getParcelable<PackagesEntity>("package")!!
        setupToolbar(title = packageDetails.name)

        binding.backBtn.setOnClickListener { finish() }

        binding.clContainer.setBackgroundResource(
            when (packageDetails.extension) {
                "AMA" -> R.drawable.ama_bg
                "PRZ" -> R.drawable.prz_bg
                "SLV" -> R.drawable.slv_bg
                "PLT" -> R.drawable.plt_bg
                "GLD" -> R.drawable.gold_bg
                else -> R.drawable.prz_bg
            }
        )
        binding.tvName.text = packageDetails.name
        binding.tvPrice.text = "${packageDetails.price} جنيه - للفرد"
        binding.tvDescription.text = packageDetails.description

        binding.subscribeBtn.setOnClickListener {
            val intent = Intent(this, SubscriptionActivity::class.java)
            intent.putExtra("name", packageDetails.name )
            intent.putExtra("price", packageDetails.price )
            intent.putExtra("vat", packageDetails.vat )
            intent.putExtra("total", packageDetails.total )
            intent.putExtra("serviceCode", packageDetails.serviceCode )
            intent.putExtra("maxFollowers", packageDetails.maxFollower )
            intent.putExtra("serviceActionCode", packageDetails.serviceActionCode )
            intent.putExtra("subscriptionMode", true )
            startActivity(intent)
        }
    }
}