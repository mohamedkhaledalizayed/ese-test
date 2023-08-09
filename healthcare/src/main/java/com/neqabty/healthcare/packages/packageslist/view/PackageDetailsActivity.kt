package com.neqabty.healthcare.packages.packageslist.view


import android.content.Intent
import android.os.Bundle
import android.text.Html
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPackageDetailsBinding
import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
import com.neqabty.healthcare.packages.subscription.view.UserDataActivity
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

        binding.tvName.text = packageDetails.name
        binding.tvPrice.text = "${packageDetails.price}"
        binding.tvDescription.text = packageDetails.description
        var details = ""
        for (item in packageDetails.details){
            details += "${item.title} <br> ${item.description} <br> <br>"
        }
        binding.details.text = Html.fromHtml(details)

        binding.subscribeBtn.setOnClickListener {
            val intent = Intent(this, UserDataActivity::class.java)
            intent.putExtra("package", packageDetails)
            startActivity(intent)
        }
    }
}