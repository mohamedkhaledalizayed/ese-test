package com.neqabty.presentation.ui.updateDataDetails

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.UpdateDataDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.entities.InquireUpdateUserDataUI
import com.neqabty.presentation.ui.common.PhotosAdapter
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.update_data_details_fragment.*
import java.io.*
import java.util.*
import javax.inject.Inject

class UpdateDataDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<UpdateDataDetailsFragmentBinding>()
    private var adapter by autoCleared<PhotosAdapter>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var userDataInquire: InquireUpdateUserDataUI

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1

    private var captureImage = false

    private var photosList: MutableList<PhotoUI> = mutableListOf<PhotoUI>()
    lateinit var updateDataDetailsViewModel: UpdateDataDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.update_data_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateDataDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(UpdateDataDetailsViewModel::class.java)

        updateDataDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        updateDataDetailsViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                updateDataDetailsViewModel.verifyUser(userDataInquire.oldRefID,binding.edMobileNumber.text.toString())
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        initializeViews()
    }

    private fun handleViewState(state: UpdateDataDetailsViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        activity?.invalidateOptionsMenu()
        if (!state.isLoading && state.code != "") {
            userDataInquire.nationalID = edNationalID.text.toString()
            userDataInquire.phoneNumber = edMobileNumber.text.toString()
            navController().navigate(
                    UpdateDataDetailsFragmentDirections.updateDataVerification(userDataInquire , state.code)
            )
            state.code = ""
        }
    }

    fun initializeViews() {
        val params = UpdateDataDetailsFragmentArgs.fromBundle(arguments!!)
        userDataInquire = params.userDataInquire

        userDataInquire?.let {
//            var tempMember = it.copy()
//            tempMember.engineerName = getString(R.string.name_title) + " " + it.engineerName
//            tempMember.expirationDate = getString(R.string.expiration_date_title) + " " + it.billDate
//            tempMember.amount = getString(R.string.amount_title) + " " + it.amount + " ج"
            binding.userData = it
        }

        binding.bAddPhoto.setOnClickListener {
            if (photosList.size < 3)
                addPhoto()
        }

        bUpdate.setOnClickListener {
            updateDataDetailsViewModel.verifyUser(userDataInquire.oldRefID,binding.edMobileNumber.text.toString())
        }


        val adapter = PhotosAdapter(dataBindingComponent, appExecutors) { photo ->
            photosList.remove(photo)
            adapter.notifyDataSetChanged()
        }
        this.adapter = adapter
        binding.rvPhotos.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data!!)
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA) {
            grantCameraPermission()
        }
    }
// region

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
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
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

    private fun onCaptureImageResult(data: Intent) {
        var thumbnail: Bitmap = data.getExtras().get("data") as Bitmap
        var bytes: ByteArrayOutputStream = ByteArrayOutputStream()
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 20, bytes)

        var name = System.currentTimeMillis().toString() + ".jpg"
        var destination: File = File(Environment.getExternalStorageDirectory(), name)

        var fo: FileOutputStream
        try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        photosList.add(PhotoUI(Environment.getExternalStorageDirectory().toString(), name))
        adapter.submitList(photosList)
        adapter.notifyDataSetChanged()
    }

    fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val path: String = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val name = Calendar.getInstance().getTimeInMillis().toString() + ".jpg"
        val directory = File(path)
        Log.d("fee", directory.toString())
        if (!directory.exists())
            directory.mkdirs()

        try {
            val f = File(directory, name)
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(requireContext(), arrayOf(f.getPath()), arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return PhotoUI(path, name)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return PhotoUI(path, name)
    }

    fun getPhoto(index: Int): File? {
        if (photosList.size > index) {
            var photoUI: PhotoUI? = photosList.get(index)
            photoUI?.let { return File(photoUI.path, photoUI.name) } ?: null
        }
        return null
    }

    private fun showPickPhotoAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.pick_photo))
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

// endregion

    fun navController() = findNavController()
}
