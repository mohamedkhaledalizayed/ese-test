package com.neqabty.presentation.ui.medicalRenewFollowerDetails

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64.NO_WRAP
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewFollowerDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.util.ImageUtils
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_renew_follower_details_fragment.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MedicalRenewFollowerDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewFollowerDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    val myCalendar = Calendar.getInstance()

    lateinit var followerItem: MedicalRenewalUI.FollowerItem

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1

    private var captureImage = false
    private var photoFileName = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_renew_follower_details_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeViews()
    }

    private fun initializeViews() {
        val params = MedicalRenewFollowerDetailsFragmentArgs.fromBundle(arguments!!)
        followerItem = params.followerItem
        binding.followerItem = followerItem

        updateEditPhotoTitle()
        setBirthDate()
        setAvatar()
        bEditPhoto.setOnClickListener { addPhoto() }

        bSave.setOnClickListener { navigateBackWithResult() }
    }

    //region
    private fun addPhoto() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle(getString(R.string.select))
        val pictureDialogItems = arrayOf(getString(R.string.gallery), getString(R.string.camera))
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> galleryIntent()
                1 -> grantCameraPermission()
            }
        }
        pictureDialog.show()
    }

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE)
    }


    fun grantCameraPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            captureImage = true
            requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA)
        } else {
            captureImage = false
            cameraIntent()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA) {
            grantCameraPermission()
        }
    }

    private fun cameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    ImageUtils.createImageFile(requireContext())
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.neqabty.fileprovider",
                            it
                    )
                    photoFileName = photoFile.name
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA)
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data!!)
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult()
        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, data.data)
            ivPhoto.setImageURI(null)
            binding.ivPhoto.setImageBitmap(bitmap)
            updateEditPhotoTitle()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun onCaptureImageResult() {
        var bitmap = BitmapFactory.decodeFile(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + photoFileName)
        ivPhoto.setImageURI(null)
        binding.ivPhoto.setImageBitmap(bitmap)
        updateEditPhotoTitle()
    }

    private fun updateEditPhotoTitle() {
        if (followerItem.pic.isNullOrBlank() && ImageUtils.getByteArrayFromImageView(ivPhoto) == null)
            bEditPhoto.text = getString(R.string.add_follower_photo)
        else
            bEditPhoto.text = getString(R.string.edit_follower_photo)
    }

    private fun setBirthDate() {
        edBirthDate.setOnClickListener {
            val year = myCalendar.get(Calendar.YEAR)
            val month = myCalendar.get(Calendar.MONTH)
            val day = myCalendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }, year, month, day)

            datePicker.show()
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd"; // In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edBirthDate.setText(sdf.format(myCalendar.getTime()))
    }

    private fun setAvatar() {
        if (followerItem.pic.isNullOrBlank()) return
//        if (followerItem.pic?.contains("http", true) == true) {
//            Glide.with(this).load(Uri.parse(followerItem.pic)).into(ivPhoto)
//            return
//        }
        var byteArray = android.util.Base64.decode(followerItem.pic, NO_WRAP)
        ivPhoto.setImageBitmap(null)
        ivPhoto.setImageBitmap(ImageUtils.getBitmapFromByteArray(byteArray!!))
    }

    private fun navigateBackWithResult() {
        val intent = Intent()
        val bundle = Bundle()
        followerItem.name = binding.edName.text.toString()
        followerItem.birthDate = binding.edBirthDate.text.toString()
        try {
            followerItem.pic = android.util.Base64.encodeToString(ImageUtils.getByteArrayFromImageView(ivPhoto), NO_WRAP).replace("\\", "")
//        followerItem.pic = "145709_2_2.jpg"
        }catch (e: Exception){
            showAlert(getString(R.string.invalid_data))
            return
        }
        if (followerItem.name.isNullOrBlank() || followerItem.birthDate.isNullOrBlank() || (followerItem.pic.isNullOrBlank())) {
            showAlert(getString(R.string.invalid_data))
            return
        }

        bundle.putParcelable("followerItem", followerItem)
        intent.putExtras(bundle)
        parentFragmentManager?.setFragmentResult("bundle", bundle)
        navController().navigateUp()
    }

// endregion

    fun navController() = findNavController()
}
