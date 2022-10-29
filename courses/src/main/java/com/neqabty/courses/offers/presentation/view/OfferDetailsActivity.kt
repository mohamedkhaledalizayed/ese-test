package com.neqabty.courses.offers.presentation.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.courses.R
import com.example.courses.databinding.ActivityOfferDetailsBinding
import com.google.gson.Gson
import com.neqabty.courses.core.data.Constants.OFFERDETAILS
import com.neqabty.courses.core.ui.BaseActivity
import com.neqabty.courses.core.utils.FileUtils
import com.neqabty.courses.core.utils.Status
import com.neqabty.courses.offers.data.model.CourseOfferReschedule
import com.neqabty.courses.offers.data.model.RescheduleRequestBody
import com.neqabty.courses.offers.presentation.model.ErrorUIModel
import com.neqabty.courses.offers.domain.entity.OfferEntity
import com.neqabty.courses.offers.presentation.OfferDatesAdapter
import com.neqabty.courses.offers.presentation.OfferPricingAdapter
import com.neqabty.courses.offers.presentation.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody



@AndroidEntryPoint
class OfferDetailsActivity : BaseActivity<ActivityOfferDetailsBinding>(), IOnSendClicked {

    private val offersViewModel: OffersViewModel by viewModels()

    override fun getViewBinding() = ActivityOfferDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "تفاصيل العرض")

        val offer = intent.getParcelableExtra(OFFERDETAILS) as OfferEntity?
        offer?.let {
            binding.titleTV.text = it.title
            binding.startDateValue.text = it.startDate
            binding.endDateValue.text = it.endDate
            binding.contactValue.text = it.contact
            binding.peopleValue.text = it.numOfTrainees.toString()
            binding.addressValue.text = it.address
            binding.datesRv.adapter = OfferDatesAdapter(it.appointmentEntities)
            binding.pricingRv.adapter = OfferPricingAdapter(it.pricingEntities) { index ->
//                ReservationDialog(this, it, index).show()
            }
        }

        offersViewModel.reservationStatus.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data != null){
                            Toast.makeText(this, "تم حجز الكورس بنجاح.", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        val error = Gson().fromJson(resource.message.toString(), ErrorUIModel::class.java)
                        Toast.makeText(this@OfferDetailsActivity, error.detail, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        offersViewModel.requestStatus.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data != null){
                            Toast.makeText(this, "تم إرسال طلبك بنجاح.", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this@OfferDetailsActivity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.bookCourseBtn.setOnClickListener {

            if (binding.phone.text.toString().isNullOrEmpty()){
                Toast.makeText(this, "من فضلك إدخل رقم الهاتف.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (imageUrl == null){
                Toast.makeText(this, "من فضلك إختر الصورة.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val image: MultipartBody.Part = prepareFilePart("image", imageUrl!!)

            offersViewModel.reservations(
                mobile = binding.phone.text.toString(),
                image = image,
                studentMobile = "+201128236545",
                notes = binding.notes.text.toString(),
                offer = offer?.id.toString())
        }

        binding.uploadImage.setOnClickListener {
            checkPermissionsAndOpenFilePicker()
        }
        binding.changeDate.setOnClickListener {
            val fm: FragmentManager = supportFragmentManager
            val dialog = ChangeDateDialog()
            dialog.show(fm, "")
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @NonNull
    private fun prepareFilePart(
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part {
        val file = FileUtils.getFile(this, fileUri)
        val requestFile = RequestBody.create(
            contentResolver.getType(fileUri).toString().toMediaTypeOrNull(),
            file
        )
        return MultipartBody.Part.createFormData(partName, file.name, requestFile);
    }

    private val PERMISSIONS_REQUEST_CODE = 15

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    private fun checkPermissionsAndOpenFilePicker() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(permission),
                    PERMISSIONS_REQUEST_CODE
                )
            }
        } else {
            showChooser()
        }
    }

    private val REQUEST_CODE = 101
    private var imageUrl: Uri? = null

    private fun showChooser() {
        val intent = Intent()
        intent.type = "image/*";
        intent.action = Intent.ACTION_GET_CONTENT;
        startActivityForResult(
            Intent.createChooser(intent,
            "Select Picture"), REQUEST_CODE);
    }


    private fun showError() {
        Toast.makeText(this, "Allow external storage reading", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUrl = data.data
            binding.image.setImageResource(R.drawable.ic_baseline_check_circle_24)
            binding.imageText.setTextColor(resources.getColor(R.color.black))
            binding.imageText.text = "تم إرفاق الصورة بنجاح."
        }
    }

    override fun onClick(note: String) {
        offersViewModel.rescheduleRequest(RescheduleRequestBody(CourseOfferReschedule(
            note = note,
            offer = 2,
            studentMobile = "+201022162466"
        )))
    }
}