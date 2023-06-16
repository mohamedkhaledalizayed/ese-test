package com.neqabty.healthcare.sustainablehealth.mypackages.presentation


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.view.View
import androidx.activity.viewModels
import com.neqabty.healthcare.core.packages.FollowersAdapter
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySubscriptionDetailsBinding
import com.tejpratapsingh.pdfcreator.activity.PDFViewerActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class SubscriptionDetailsActivity : BaseActivity<ActivitySubscriptionDetailsBinding>() {

    private var mAdapter = FollowersAdapter()
    private var mInsuranceAdapter = InsuranceAdapter()
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivitySubscriptionDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "تفاصيل الاشتراك")

        binding.followersRecyclerView.adapter = mAdapter
        binding.insuranceRecyclerView.adapter = mInsuranceAdapter
        profileViewModel.getProfile(sharedPreferences.mobile)
        profileViewModel.userData.observe(this){
            it.let { resource ->

                when(resource.status){
                    Status.LOADING ->  {
//                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
//                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.status){
                        mAdapter.submitList(resource.data.data!!.subscribedPackages[0].packages.followers)
                            mInsuranceAdapter.submitList(resource.data.data.subscribedPackages[1].packages.insuranceDocs)
//                        userNumber = resource.data.data.client.userNumber
//                        binding.qrCode.loadSVG(resource.data!!.data.client.qrCode)
                        }else{
//                        binding.noPackagesLayout.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR ->{
//                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }

        mInsuranceAdapter.onItemClickListener = object : InsuranceAdapter.OnItemClickListener{
            override fun setOnItemClickListener(item: String) {
                convertBase64ToPDF(item)
            }

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

}