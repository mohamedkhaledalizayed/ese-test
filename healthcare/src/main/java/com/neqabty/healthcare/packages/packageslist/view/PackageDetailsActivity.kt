package com.neqabty.healthcare.packages.packageslist.view


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPackageDetailsBinding
import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
import com.neqabty.healthcare.packages.subscription.view.UserDataActivity
import com.tejpratapsingh.pdfcreator.activity.PDFViewerActivity
import dagger.hilt.android.AndroidEntryPoint
import w0.a
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class PackageDetailsActivity : BaseActivity<ActivityPackageDetailsBinding>() {

    private val packagesViewModel: PackagesViewModel by viewModels()
    private val mAdapter: InsuranceAdapter = InsuranceAdapter()
    override fun getViewBinding() = ActivityPackageDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val packageDetails = intent.extras?.getParcelable<PackagesEntity>("package")!!
        setupToolbar(title = packageDetails.name)

        binding.rvDocs.adapter = mAdapter
        packagesViewModel.getInsuranceDocs(packageDetails.id)
        packagesViewModel.insuranceDocs.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.status){
                            mAdapter.submitList(resource.data.data)
                        }else{
                            Toast.makeText(this@PackageDetailsActivity, "حدث خطاء اثناء تحميل الشروط و الاحكام.", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Log.e("message", "${resource.message}")
                    }
                }
            }
        }

        mAdapter.onItemClickListener = object :
            InsuranceAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {
                if (item.isEmpty()){
                    Toast.makeText(this@PackageDetailsActivity, "حدث خطاء.", Toast.LENGTH_LONG).show()
                    return
                }
                convertBase64ToPDF(item)
            }
        }


        binding.tvName.text = packageDetails.name
        binding.tvPrice.text = "${packageDetails.price}"
        binding.tvDescription.text = packageDetails.description
        var details = ""
        for (item in packageDetails.details){
            details += "${item.title} <br> ${item.description} <br> <br>"
        }
        binding.details.text = Html.fromHtml(details)



        binding.subscribeBtn.setOnClickListener {
            if (!binding.terms.isChecked){
                Toast.makeText(this, "من فضلك إقرا الشروط و الاحكام.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (sharedPreferences.isAuthenticated){
                val intent = Intent(this, UserDataActivity::class.java)
                intent.putExtra("package", packageDetails)
                startActivity(intent)
            }else{
                askForLogin()
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