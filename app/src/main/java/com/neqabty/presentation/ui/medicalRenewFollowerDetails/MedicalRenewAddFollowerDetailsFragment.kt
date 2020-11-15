package com.neqabty.presentation.ui.medicalRenewFollowerDetails

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64.NO_WRAP
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewAddFollowerDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.ui.common.PhotosAdapter
import com.neqabty.presentation.util.ImageUtils
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_renew_add_follower_details_fragment.*
import kotlinx.android.synthetic.main.medical_renew_add_follower_details_fragment.bEditPhoto
import kotlinx.android.synthetic.main.medical_renew_add_follower_details_fragment.bSave
import kotlinx.android.synthetic.main.medical_renew_add_follower_details_fragment.edBirthDate
import kotlinx.android.synthetic.main.medical_renew_add_follower_details_fragment.ivPhoto
import kotlinx.android.synthetic.main.medical_renew_follower_details_fragment.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MedicalRenewAddFollowerDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewAddFollowerDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    val myCalendar = Calendar.getInstance()
    var relationsList: MutableList<String>? = mutableListOf("زوجة", "والد", "والدة", "ابناء اقل من ١٦ سنة", "ابناء بعد سن ١٨ سنة", "ابناء بعد سن ٢٥ سنة")
    var hintsList: MutableList<String>? = mutableListOf("لإضافة الزوجة برجاء إرفاق صورة قسيمة الزواج او صورة بطاقة الرقم القومي وصورة شخصية",
            "لإضافة الوالد برجاء إرفاق صورة البطاقة الشخصية للوالد وصورة شخصية", "لإضافة الوالدة برجاء إرفاق صورة شهادة ميلاد المهندس والبطاقة الشخصية للوالدة و صورة شخصية",
            "لإضافة أبناء اقل من ١٦ سنة برجاء إرفاق شهادة الميلاد وصورة شخصية", "لإضافة أبناء بعد سن ١٨ سنة برجاء إرفاق صورة بطاقة الرقم القومي + صورة شخصية",
            "لإضافة أبناء بعد سن ٢٥ سنة برجاء إرفاق صورة بطاقة الرقم القومي وما يفيد انه طالب و صورة شخصية")
    var selectedRelation = ""

    lateinit var followerItem: MedicalRenewalUI.FollowerItem

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private var isForPP = true

    private var photosAdapter by autoCleared<PhotosAdapter>()
    private var captureImage = false
    private var photoFileName = ""
    private var photosList: MutableList<PhotoUI> = mutableListOf<PhotoUI>()

    lateinit var photoFileURI: Uri
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_renew_add_follower_details_fragment,
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

        setBirthDate()
        renderRelations()
        updateEditPhotoTitle()
        bEditPhoto.setOnClickListener {
            isForPP = true
            addPhoto()
        }
        bAttachPhoto.setOnClickListener {
            if (photosList.size < 2) {
                isForPP = false
                addPhoto()
            }
        }

        val adapter = PhotosAdapter(dataBindingComponent, appExecutors) { photo ->
            photosList.remove(photo)
            photosAdapter.notifyDataSetChanged()
            if (photosList.size < 2)
                bAttachPhoto.visibility = View.VISIBLE
        }
        this.photosAdapter = adapter
        binding.rvPhotos.adapter = adapter

        setAvatar()
        renderAttachments()
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
                    photoFileURI = photoURI
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
            if (isForPP) {
                ivPhoto.setImageBitmap(bitmap)
                updateEditPhotoTitle()
            } else {
                val photoUI = saveImage(bitmap)
                photosList.add(photoUI)
                photosAdapter.submitList(photosList)
                photosAdapter.notifyDataSetChanged()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun onCaptureImageResult() {
        if (isForPP) {
            var bitmap = BitmapFactory.decodeFile(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + photoFileName)
            ivPhoto.setImageBitmap(bitmap)
            updateEditPhotoTitle()
        }else {
            photosList.add(PhotoUI(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), photoFileName, photoFileURI))
            val bitmap: Bitmap = BitmapFactory.decodeFile(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + photoFileName)
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
            val bos = BufferedOutputStream(FileOutputStream(File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), photoFileName)))
            bos.write(bytes.toByteArray())
            bos.flush()
            bos.close()
            photosAdapter.submitList(photosList)
            photosAdapter.notifyDataSetChanged()

            if (photosList.size == 2)
                bAttachPhoto.visibility = View.GONE
        }
    }

    fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes)
        val path: String = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val name = Calendar.getInstance().getTimeInMillis().toString() + ".jpg"
        val directory = File(path)
        if (!directory.exists())
            directory.mkdirs()

        try {
            val f = File(directory, name)
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(requireContext(), arrayOf(f.getPath()), arrayOf("image/jpeg"), null)
            fo.close()
            return PhotoUI(path, name, Uri.parse(path + "/" + name))
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return PhotoUI(path, name, null)
    }


    fun getPhoto(index: Int): File? {
        if (photosList.size > index) {
            var photoUI: PhotoUI? = photosList.get(index)
            photoUI?.let { return File(photoUI.path, photoUI.name) } ?: null
        }
        return null
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
        val myFormat = "MM/dd/yy"; // In which you need put here
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

    private fun renderAttachments() {
        photosList.clear()
        if (followerItem.attachments.size > 0) {
            for (i in 0 until followerItem.attachments.size) {
                var byteArray = android.util.Base64.decode(followerItem.attachments[i], NO_WRAP)
                val photoUI = saveImage(ImageUtils.getBitmapFromByteArray(byteArray)!!)
                photosList.add(photoUI)
//                (rvPhotos.findViewHolderForAdapterPosition(i)!!.itemView.findViewById(R.id.ivImage) as ImageView).setImageBitmap(ImageUtils.getBitmapFromByteArray(byteArray!!))
            }
            if (photosList.size == 10)
                bAttachPhoto.visibility = View.GONE

            (rvPhotos.adapter as PhotosAdapter).submitList(photosList)
        }
//        if (followerItem.pic.isNullOrBlank()) return
//        if (followerItem.pic?.contains("http", true) == true) {
//            Glide.with(this).load(Uri.parse(followerItem.pic)).into(ivPhoto)
//            return
//        }
//        var byteArray = android.util.Base64.decode(followerItem.pic, NO_WRAP)
//        ivPhoto.setImageBitmap(null)
//        ivPhoto.setImageBitmap(ImageUtils.getBitmapFromByteArray(byteArray!!))
    }


    private fun updateEditPhotoTitle() {
        if (followerItem.pic.isNullOrBlank() && ImageUtils.getByteArrayFromImageView(ivPhoto) == null)
            bEditPhoto.text = getString(R.string.add_follower_photo)
        else
            bEditPhoto.text = getString(R.string.edit_follower_photo)
    }

    fun renderRelations() {
        binding.spRelationDegree.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, relationsList!!)
        binding.spRelationDegree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedRelation = (parent.getItemAtPosition(position) as String)
                tvHint.text = hintsList?.get(position)
            }
        }
        binding.spRelationDegree.setSelection(0)
    }

    private fun navigateBackWithResult() {
        val intent = Intent()
        val bundle = Bundle()
        followerItem.isNew = true
        followerItem.name = binding.edName.text.toString()
        followerItem.birthDate = binding.edBirthDate.text.toString()
        try {
            followerItem.pic = android.util.Base64.encodeToString(ImageUtils.getByteArrayFromImageView(ivPhoto), NO_WRAP).replace("\\", "")

            followerItem.attachments.clear()
            for (i in 0 until photosList?.size!!) {
                followerItem.attachments.add(android.util.Base64.encodeToString(ImageUtils.getByteArrayFromImageView(rvPhotos.findViewHolderForAdapterPosition(i)!!.itemView.findViewById(R.id.ivImage)), NO_WRAP).replace("\\", ""))
            }
        } catch (e: Exception) {
            showAlert(getString(R.string.invalid_data))
            return
        }
        if (followerItem.name.isNullOrBlank() || followerItem.birthDate.isNullOrBlank() || photosList.size == 0 || (followerItem.pic.isNullOrBlank())) {
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
