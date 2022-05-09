package com.neqabty.presentation.ui.committees

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.CommitteesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.MultiSelectionSpinner
import com.neqabty.presentation.common.SpinnerModel
import com.neqabty.presentation.entities.CommitteesLookupUI
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.util.ImageUtils
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class CommitteesFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<CommitteesFragmentBinding>()

    private val committeesViewModel: CommitteesViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors


    private val REQUEST_CAMERA = 0
    private val REQUEST_PDF = 12

    private var PhotoFileName = ""
    private var PDFFileName = ""
    lateinit var photoFileURI: Uri
    lateinit var pdfFileURI: Uri
    private var captureImage = false

    private var photosList: MutableList<PhotoUI> = mutableListOf<PhotoUI>()

    var selectedIndex = 0

    var degree: String = ""
    var status: String = ""
    var committeeIDs: List<Int> = listOf()
    var isSubmitted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.committees_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        committeesViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        committeesViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                committeesViewModel.getLookups()
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })
        committeesViewModel.getLookups()
        initializeViews()
    }

    private fun initializeViews() {
        binding.edName.setText(sharedPref.name)
        binding.edMobile.setText(sharedPref.mobile)
        binding.bSend.setOnClickListener {
            if (photosList[2].name == null) {
                showPickPhotoAlert()
            } else if (!binding.edNationalID.text.toString().matches(Regex("(2|3)[0-9][0-9][0-1][0-9][0-3][0-9](01|02|03|04|05|11|12|13|14|15|16|17|18|19|21|22|23|24|25|26|27|28|29|31|32|33|34|35|88)\\d\\d\\d\\d\\d"))) {
                showAlert(getString(R.string.invalid_national_id))
            } else if (!binding.edEmail.text.toString().matches(android.util.Patterns.EMAIL_ADDRESS.toRegex())) {
                showAlert(getString(R.string.invalid_email))
            } else if (binding.edAddress.text.isBlank() || binding.edJob.text.isBlank() || binding.edJobDetails.text.isBlank() || binding.edUni.text.isBlank() || binding.edDepartment.text.isBlank()){
                showAlert(getString(R.string.invalid_data))
            } else if (committeeIDs.size > 3 || committeeIDs.isEmpty()){
                showAlert(getString(R.string.committees_max_three))
            } else {
                isSubmitted = true
                committeesViewModel.sendCommitteesRequest(
                    binding.edName.text.toString(),
                    sharedPref.user,
                    binding.edMobile.text.toString(),
                    binding.edEmail.text.toString(),
                    binding.edNationalID.text.toString(),
                    binding.edAddress.text.toString(),
                    binding.edUni.text.toString(),
                    binding.spDegrees.selectedItem.toString(),
                    binding.spStatus.selectedItem.toString(),
                    committeeIDs,
                    sharedPref.userSectionID!!,
                    sharedPref.userSyndicateID!!,
                    binding.edDepartment.text.toString(),
                    sharedPref.userSection!!,
                    binding.edJob.text.toString(),
                    binding.edJobDetails.text.toString(),
                    photosList.size,
                    getPhoto(0),
                    getPhoto(1),
                    getPhoto(2)
                )
            }
        }

        photosList.add(0, PhotoUI(null, null, null))
        photosList.add(1, PhotoUI(null, null, null))
        photosList.add(2, PhotoUI(null, null, null))

        binding.ibAddPhoto1.setOnClickListener {
            selectedIndex = 0

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE), REQUEST_PDF)
            } else {
                addPDF()
            }
        }
        binding.ibAddPhoto2.setOnClickListener {
            selectedIndex = 1
            addPhoto()
        }
        binding.ibAddPhoto3.setOnClickListener {
            selectedIndex = 2
            addPhoto()
        }
    }

    private fun handleViewState(state: CommitteesViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading && isSubmitted && state.message != null) {
            isSubmitted = false
            showSuccessAlert(state.message!!)
        } else if (!state.isLoading && state.lookups != null) {
            binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            renderLookups(state.lookups!!)
        }
    }

    private fun addPDF() {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
            val storageDir: File = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!
            val file = File.createTempFile(
                "PDF_${timeStamp}_", /* prefix */
                ".pdf", /* suffix */
                storageDir /* directory */
            )

        file?.also {
            val pdfURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.neqabty.fileprovider",
                it
            )
            PDFFileName = file.name
            pdfFileURI = pdfURI
        }

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(MediaStore.EXTRA_OUTPUT, pdfFileURI)
        }

        startActivityForResult(intent, REQUEST_PDF)
    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data!!)
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult()
            else if (requestCode == REQUEST_PDF)
                onPickPDFResult(data!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA) {
            grantCameraPermission()
        }
    }
    //region
    fun renderLookups(lookups: CommitteesLookupUI) {
        renderDegrees(lookups.degreesList)
        renderStatus(lookups.maritalStatusList)
        renderCommittees(lookups.committeesList)
    }

    fun renderDegrees(degrees: List<String>) {
        binding.spDegrees.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, degrees)
        binding.spDegrees.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                degree = (parent.getItemAtPosition(position) as String)
            }
        }
        binding.spDegrees.setSelection(0)
    }

    fun renderStatus(statusList: List<String>) {
        binding.spStatus.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, statusList)
        binding.spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                status = (parent.getItemAtPosition(position) as String)
            }
        }
        binding.spStatus.setSelection(0)
    }

    fun renderCommittees(committees: List<CommitteesLookupUI.CommitteeItem>) {
        binding.spCommittees.setItems(committees.map { item -> SpinnerModel(item.id, item.name!!, isSelected = false) })
        binding.spCommittees.setType(0)
        binding.spCommittees.setOnItemsSelectedListener(object :
            MultiSelectionSpinner.OnMultipleItemsSelectedListener {
            override fun selectedItems(items: List<SpinnerModel>) {
                committeeIDs = items.map { item -> item.id }
            }
        })
        binding.spCommittees.setSelection(0)
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
                    PhotoFileName = photoFile.name
                    photoFileURI = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA)
                }
            }

        }
    }

    fun grantCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this.context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            captureImage = true
            requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA)
        } else {
            captureImage = false
            cameraIntent()
        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        if (data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, data.data)
                val photoUI = saveImage(bitmap)
                addToPhotos(photoUI)
                updateIcons()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun onCaptureImageResult() {
        addToPhotos(PhotoUI(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), PhotoFileName, photoFileURI))
        val bitmap: Bitmap = BitmapFactory.decodeFile(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + PhotoFileName)
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val bos = BufferedOutputStream(FileOutputStream(File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), PhotoFileName)))
        bos.write(bytes.toByteArray())
        bos.flush()
        bos.close()
        updateIcons()
    }

    private fun onPickPDFResult(data: Intent){
        val photoUI = PhotoUI(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString(), PDFFileName, pdfFileURI)
        addToPhotos(photoUI)
        val inputStream = requireContext().contentResolver.openInputStream(data.data!!)
        inputStream.use { input ->
            getPhoto(0)!!.outputStream().use { output ->
                input!!.copyTo(output)
            }
        }
        updateIcons()
    }

    fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
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
            return PhotoUI(path, name, null)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return PhotoUI(path, name, null)
    }

    fun getPhoto(index: Int): File? {
        if (photosList.size > index) {
            var photoUI: PhotoUI? = photosList.get(index)
            photoUI?.let {
                return try {
                    File(photoUI.path, photoUI.name)
                } catch (e: Exception) {
                    null
                }
            }
        }
        return null
    }

    fun showSuccessAlert(message: String) {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setMessage(message)
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().navigateUp()
        }
        var dialog = builder?.create()
        dialog?.show()
    }


    private fun showPickPhotoAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.committees_attach_photos))
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun updateIcons() {
        when (selectedIndex) {
            0 -> binding.ibAddPhoto1.setImageResource(R.drawable.ic_done)
            1 -> binding.ibAddPhoto2.setImageResource(R.drawable.ic_done)
            2 -> binding.ibAddPhoto3.setImageResource(R.drawable.ic_done)
        }
    }

    private fun addToPhotos(photoUI: PhotoUI) {
        photosList[selectedIndex] = photoUI
    }

// endregion

    fun navController() = findNavController()
}
