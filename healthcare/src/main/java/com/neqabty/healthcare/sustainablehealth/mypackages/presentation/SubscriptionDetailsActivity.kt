package com.neqabty.healthcare.sustainablehealth.mypackages.presentation


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.packages.FollowersAdapter
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySubscriptionDetailsBinding
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile.PackageEntity
import com.neqabty.healthcare.sustainablehealth.payment.view.SehaPaymentActivity
import com.tejpratapsingh.pdfcreator.activity.PDFViewerActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class SubscriptionDetailsActivity : BaseActivity<ActivitySubscriptionDetailsBinding>() {

    private var mAdapter = FollowersAdapter()
    private var mInsuranceAdapter = InsuranceAdapter()
    private val profileViewModel: ProfileViewModel by viewModels()
    private var maxFollower = 0
    private lateinit var packageInfo: PackageEntity
    override fun getViewBinding() = ActivitySubscriptionDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "تفاصيل الاشتراك")


        maxFollower = intent.getIntExtra("maxFollower", 0)
        binding.followersRecyclerView.adapter = mAdapter
        binding.insuranceRecyclerView.adapter = mInsuranceAdapter

        profileViewModel.userData.observe(this){
            it.let { resource ->

                when(resource.status){
                    Status.LOADING ->  {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.status){
                            binding.mainContainer.visibility = View.VISIBLE
                            packageInfo = resource.data.data!!.subscribedPackages
                                .filter { it.packages.id == intent.getStringExtra("packageId")!!}[0].packages

                            if (!packageInfo.paid){
                                binding.orderStatus.text = "غير مكتملة"
                                binding.orderStatus.background = resources.getDrawable(R.drawable.order_status_canceled_bg)
                                handlePayment()
                            }

                            handlePackageResources()
                            mAdapter.submitList(packageInfo.followers)
                            mInsuranceAdapter.submitList(packageInfo.insuranceDocs)
                            if (mAdapter.itemCount == maxFollower){
                                binding.addFollowers.visibility = View.GONE
                            }
                        }else{
//                        binding.noPackagesLayout.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR ->{
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }

        mAdapter.onItemClickListener = object : FollowersAdapter.OnItemClickListener {

            override fun setOnDeleteFollowerListener(subscriberId: String, followerId: Int) {
                deleteFollower("هل تريد حذف هذا التابع !", followerId, subscriberId)
            }

        }
        mInsuranceAdapter.onItemClickListener = object : InsuranceAdapter.OnItemClickListener{
            override fun setOnItemClickListener(item: String) {
                convertBase64ToPDF(item)
            }

        }

        binding.addFollowers.setOnClickListener {
            if (mAdapter.itemCount == maxFollower){
                Toast.makeText(this@SubscriptionDetailsActivity, "لا يمكن اضافة تابع جديد.", Toast.LENGTH_LONG).show()
            }else{
                addFollower(intent.getStringExtra("packageId") ?: "", intent.getStringExtra("subscriberId") ?: "")
            }
        }

        profileViewModel.followerStatus.observe(this){
            it.let { resource ->

                when(resource.status){
                    Status.LOADING ->{
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!){
                            profileViewModel.getProfile(sharedPreferences.mobile)
                            Toast.makeText(this@SubscriptionDetailsActivity, "تم حذف التابع بنجاح.", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this@SubscriptionDetailsActivity, "لم يتم حذف التابع.", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR ->{
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this@SubscriptionDetailsActivity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    private fun handlePackageResources() {
        binding.clContainer.setBackgroundResource(
            when (packageInfo.nameEn) {
                "AMA" -> R.drawable.ama_bg
                "PRZ" -> R.drawable.prz_bg
                "SLV" -> R.drawable.slv_bg
                "PLT" -> R.drawable.plt_bg
                "GLD" -> R.drawable.gold_bg
                else -> R.drawable.prz_bg
            }
        )

        binding.tvName.text = packageInfo.nameAr
        binding.tvPrice.text = "${packageInfo.packagePrice?.toInt()} جنيه - للفرد"
        binding.tvDescription.text = packageInfo.descriptionAr
    }

    private fun handlePayment() {
        binding.orderStatus.setOnClickListener {
            val intent = Intent(this@SubscriptionDetailsActivity, SehaPaymentActivity::class.java)
            intent.putExtra("name", packageInfo.nameAr)
            intent.putExtra("price", packageInfo.packagePrice?.toDouble())
            intent.putExtra("vat", packageInfo.vat?.toDouble())
            intent.putExtra("total", packageInfo.total?.toDouble())
            intent.putExtra("serviceCode", packageInfo.serviceCode)
            intent.putExtra("serviceActionCode", packageInfo.serviceActionCode)
            startActivity(intent)
        }
    }

    private fun convertBase64ToPDF(stringBase64: String){
        val filePath = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) , "termsandconditions" + ".pdf")
        val pdfAsBytes: ByteArray = Base64.decode(stringBase64, 0)
        val os = FileOutputStream(filePath, false)
        os.write(pdfAsBytes)
        os.flush()
        os.close()

        val pdfUri = Uri.fromFile(filePath)
        val intentPdfViewer = Intent(this, PDFViewerActivity::class.java)
        intentPdfViewer.putExtra(PDFViewerActivity.PDF_FILE_URI, pdfUri)
        startActivity(intentPdfViewer)
    }

    private fun addFollower(packageId: String, subscriberId: String) {
        val intent = Intent(this, AddFollowerActivity::class.java)
        intent.putExtra("packageId", packageId)
        intent.putExtra("subscriberId", subscriberId)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getProfile(sharedPreferences.mobile)
    }

    private fun deleteFollower(message: String, followerId: Int, subscriberId: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            profileViewModel.deleteFollower(followerId, sharedPreferences.mobile, subscriberId)
            dialog.dismiss()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

}