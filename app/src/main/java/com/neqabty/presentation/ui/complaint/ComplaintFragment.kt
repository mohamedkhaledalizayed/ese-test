package com.neqabty.presentation.ui.complaint

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.ComplaintFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.ComplaintTypeUI
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.ui.common.PhotosAdapter
import com.neqabty.presentation.util.ImageUtils
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.presentation.util.call
import kotlinx.android.synthetic.main.complaint_fragment.*
import java.io.*
import javax.inject.Inject

class ComplaintFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<ComplaintFragmentBinding>()

    @Inject
    lateinit var complaintViewModel: ComplaintViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    private var adapter by autoCleared<PhotosAdapter>()
    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1

    private var PhotoFileName = ""
    lateinit var photoFileURI: Uri

    private var captureImage = false

    private var photosList: MutableList<PhotoUI> = mutableListOf<PhotoUI>()

    var complaintsTypesList: List<ComplaintTypeUI>? = mutableListOf()
    var complaintsSubTypesList: List<ComplaintTypeUI>? = mutableListOf()
    var complaintsTypeID: Int = 0
    var complaintsSubTypeID: Int = 0
    var isSubmitted: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showAds(Constants.AD_COMPLAINTS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.complaint_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        complaintViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ComplaintViewModel::class.java)

        complaintViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        complaintViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                complaintViewModel.getTypes()
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })
        complaintViewModel.getTypes()
        initializeViews()
    }

    private fun initializeViews() {
        binding.tvDescription.setOnClickListener {
            tvDescription.call(Constants.CALL_CENTER, requireContext())
        }
        binding.edMobile.setText(PreferencesHelper(requireContext()).mobile)
        binding.bSend.setOnClickListener {
            isSubmitted = true
            complaintViewModel.createComplaint(edName.text.toString(), edMobile.text.toString(), complaintsTypeID.toString(), complaintsSubTypeID.toString(), edBody.text.toString(), PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()).user,
                    photosList.size, getPhoto(0), getPhoto(1), getPhoto(2), getPhoto(3))
        }

        binding.bAddPhoto.setOnClickListener {
            if (photosList.size < 4)
                addPhoto()
        }

        val adapter = PhotosAdapter(dataBindingComponent, appExecutors) { photo ->
            photosList.remove(photo)
            adapter.notifyDataSetChanged()
        }
        this.adapter = adapter
        binding.rvPhotos.adapter = adapter
    }

    private fun handleViewState(state: ComplaintViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading && state.message.isNotBlank() && isSubmitted) {
            isSubmitted = false
            showSuccessAlert()
        } else if (!state.isLoading && state.types != null) {
            binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            state.types?.let {
                complaintsTypesList = it
            }
            state.subTypes?.let {
                complaintsSubTypesList = it
            }
            renderTypes()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA) {
            grantCameraPermission()
        }
    }
    //region
    fun renderTypes() {
        binding.spTypes.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, complaintsTypesList!!)
        binding.spTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                complaintsTypeID = (parent.getItemAtPosition(position) as ComplaintTypeUI).id
                renderSubTypes()
            }
        }
        binding.spTypes.setSelection(0)
    }

    fun renderSubTypes() {
        if(complaintsTypeID == 1){
            binding.tvSubTypes.visibility = View.VISIBLE
            binding.spSubTypes.visibility = View.VISIBLE
        }else{
            binding.tvSubTypes.visibility = View.GONE
            binding.spSubTypes.visibility = View.GONE
            complaintsSubTypeID = -1
        }

        binding.spSubTypes.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, complaintsSubTypesList!!)
        binding.spSubTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                complaintsSubTypeID = (parent.getItemAtPosition(position) as ComplaintTypeUI).id
            }
        }
        binding.spSubTypes.setSelection(0)
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

    private fun galleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT //
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_file)), SELECT_FILE)
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
                photosList.add(photoUI)
                adapter.submitList(photosList)
                adapter.notifyDataSetChanged()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun onCaptureImageResult() {
        photosList.add(PhotoUI(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), PhotoFileName,photoFileURI))
        val bitmap: Bitmap = BitmapFactory.decodeFile(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" +PhotoFileName)
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bytes)
        val bos = BufferedOutputStream(FileOutputStream(File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), PhotoFileName)))
        bos.write(bytes.toByteArray())
        bos.flush()
        bos.close()
        adapter.submitList(photosList)
        adapter.notifyDataSetChanged()
    }

    fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.PNG, 30, bytes)
        val path: String = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val name = System.currentTimeMillis().toString() + ".jpg"
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

        return PhotoUI(path, name,null)
    }

    fun getPhoto(index: Int): File? {
        if (photosList.size > index) {
            var photoUI: PhotoUI? = photosList.get(index)
            photoUI?.let { return File(photoUI.path, photoUI.name) } ?: null
        }
        return null
    }

    fun showSuccessAlert() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(getString(R.string.complaint_sent))
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().navigateUp()
        }
        var dialog = builder?.create()
        dialog?.show()
    }

// endregion

    fun navController() = findNavController()
}
