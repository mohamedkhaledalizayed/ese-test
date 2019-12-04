package com.neqabty.presentation.ui.engineeringRecordsDetails

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
import com.neqabty.databinding.EngineeringRecordsDetailsFragmentBinding
import com.neqabty.databinding.UpdateDataDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.entities.RegisteryUI
import com.neqabty.presentation.ui.common.PhotosAdapter
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.engineering_records_details_fragment.*
import java.io.*
import java.util.*

import javax.inject.Inject

class EngineeringRecordsDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<EngineeringRecordsDetailsFragmentBinding>()
//    private var adapter by autoCleared<PhotosAdapter>()

    lateinit var engineeringRecordsDetailsViewModel: EngineeringRecordsDetailsViewModel

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var memberItem: RegisteryUI

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1

    private var captureImage = false

    private var photosList: MutableList<PhotoUI> = mutableListOf<PhotoUI>()

    var selectedIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.engineering_records_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        engineeringRecordsDetailsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(EngineeringRecordsDetailsViewModel::class.java)

        engineeringRecordsDetailsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        engineeringRecordsDetailsViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                engineeringRecordsDetailsViewModel.sendEngineeringRecordsInquiry(PreferencesHelper(requireContext()).user)
//                engineeringRecordsDetailsViewModel.requestEngineeringRecords(memberItem.fullName!! , memberItem.mobile!!, memberItem.registryTypeID!! , "5",memberItem.registryDataID!!,
//                        memberItem.lastRenewYear!!,memberItem.regDataStatusID!!.toInt(), if(memberItem.isOwner) 1 else 0  , photosList.size , getPhoto(0))
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        engineeringRecordsDetailsViewModel.sendEngineeringRecordsInquiry(PreferencesHelper(requireContext()).user)
    }


    private fun handleViewState(state: EngineeringRecordsDetailsViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading && state.memberItem != null && !state.isSuccessful) {
            state.memberItem?.registryDataID = PreferencesHelper(requireContext()).user
                when(state.memberItem?.statusCode) {
                    0 -> {
                        binding.svContent.visibility = View.VISIBLE
                        memberItem = state.memberItem!!
                        binding.memberItem = state.memberItem!!
                        state.memberItem = null
                        initializeViews()
                    }
                    else -> showAlert(state.memberItem?.msg ?: getString(R.string.user_not_allowed))
                }
        }
        if (!state.isLoading && state.isSuccessful) {
            showSuccessAlert()
        }
    }

    fun initializeViews() {

//        memberItem?.let {
////            var tempMember = it.copy()
////            tempMember.engineerName = getString(R.string.name_title) + " " + it.engineerName
////            tempMember.expirationDate = getString(R.string.expiration_date_title) + " " + it.billDate
////            tempMember.amount = getString(R.string.amount_title) + " " + it.amount + " ج"
//            binding.memberItem = it
//        }


        photosList.add(0,PhotoUI(null,null))
        photosList.add(1,PhotoUI(null,null))
        photosList.add(2,PhotoUI(null,null))

        binding.ibAddPhoto1.setOnClickListener {
            selectedIndex = 0
//            if (photosList[0].name == null)
                addPhoto()
//            else {
//                photosList[0].name = null
//                ibAddPhoto_1.setImageResource(R.drawable.ic_close)
//            }
        }

        binding.ibAddPhoto2.setOnClickListener {
            selectedIndex = 1
//            if (photosList[1].name == null)
                addPhoto()
//            else {
//                photosList[1].name = null
//                ibAddPhoto_2.setImageResource(R.drawable.ic_close)
//            }
        }

        binding.ibAddPhoto3.setOnClickListener {
            selectedIndex = 2
//            if (photosList[2].name == null)
                addPhoto()
//            else {
//                photosList[2].name = null
//                ibAddPhoto_3.setImageResource(R.drawable.ic_close)
//            }
        }

        bSubmit.setOnClickListener {
            if (photosList[0].name != null && photosList[1].name != null && photosList[2].name != null) {
                engineeringRecordsDetailsViewModel.requestEngineeringRecords(memberItem.fullName!! , PreferencesHelper(requireContext()).mobile, memberItem.registryTypeID!! , "5",memberItem.registryDataID!!,
                        memberItem.lastRenewYear!!,memberItem.regDataStatusID!!.toInt(), if(memberItem.isOwner) 1 else 0  , photosList.size , getPhoto(0), getPhoto(1), getPhoto(2))
            } else
                showPickPhotoAlert()
        }
//
//
//        val adapter = PhotosAdapter(dataBindingComponent, appExecutors) { photo ->
//            photosList.remove(photo)
//            adapter.notifyDataSetChanged()
//        }
//        this.adapter = adapter
//        binding.rvPhotos.adapter = adapter

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
                addToPhotos(photoUI)
                updateIcons()
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


        addToPhotos(PhotoUI(Environment.getExternalStorageDirectory().toString(), name))
        updateIcons()
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
        builder.setMessage(getString(R.string.pick_photos))
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }



    fun showSuccessAlert() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(getString(R.string.records_request_sent))
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }
        var dialog = builder?.create()
        dialog?.show()
    }

    private fun updateIcons(){
        when(selectedIndex){
            0 -> ibAddPhoto_1.setImageResource(R.drawable.ic_done)
            1 -> ibAddPhoto_2.setImageResource(R.drawable.ic_done)
            2 -> ibAddPhoto_3.setImageResource(R.drawable.ic_done)
        }
    }

    private fun showAlert(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(msg)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun addToPhotos(photoUI: PhotoUI){
        photosList[selectedIndex] = photoUI
    }
// endregion

    fun navController() = findNavController()
}
