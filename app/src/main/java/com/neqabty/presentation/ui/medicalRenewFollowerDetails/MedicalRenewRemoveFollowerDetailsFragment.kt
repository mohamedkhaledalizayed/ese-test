package com.neqabty.presentation.ui.medicalRenewFollowerDetails

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
import android.util.Base64.NO_WRAP
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewRemoveFollowerFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.ui.common.PhotosAdapter
import com.neqabty.presentation.util.ImageUtils
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MedicalRenewRemoveFollowerDetailsFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewRemoveFollowerFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var followerItem: MedicalRenewalUI.FollowerItem

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1

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
            R.layout.medical_renew_remove_follower_fragment,
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
        val params = MedicalRenewRemoveFollowerDetailsFragmentArgs.fromBundle(arguments!!)
        followerItem = params.followerItem
        binding.followerItem = followerItem

        binding.bAttachPhoto.setOnClickListener {
            if (photosList.size < 4) {
                addPhoto()
            }
        }

        val adapter = PhotosAdapter(dataBindingComponent, appExecutors) { photo ->
            photosList.remove(photo)
            photosAdapter.notifyDataSetChanged()
            if (photosList.size < 4)
                binding.bAttachPhoto.visibility = View.VISIBLE
        }
        this.photosAdapter = adapter
        binding.rvPhotos.adapter = adapter

        renderAttachments()
        binding.bRemove.setOnClickListener { navigateBackWithResult() }
    }

    //region
    private fun addPhoto() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle(getString(R.string.select))
        val pictureDialogItems = arrayOf(getString(R.string.gallery), getString(R.string.camera))
        pictureDialog.setItems(
            pictureDialogItems
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
        startActivityForResult(
            Intent.createChooser(intent, getString(R.string.select_file)),
            SELECT_FILE
        )
    }


    fun grantCameraPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            captureImage = true
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), REQUEST_CAMERA
            )
        } else {
            captureImage = false
            cameraIntent()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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
                data?.let { onSelectFromGalleryResult(data) }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult()
        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {
        try {
            val bitmap =
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, data.data)
            val photoUI = saveImage(bitmap)
            photosList.add(photoUI)
            photosAdapter.submitList(photosList)
            photosAdapter.notifyDataSetChanged()
            updateAttachPhotoVisibility()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun onCaptureImageResult() {
        photosList.add(
            PhotoUI(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(),
                photoFileName,
                photoFileURI
            )
        )
        val bitmap: Bitmap = BitmapFactory.decodeFile(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .toString() + "/" + photoFileName
        )
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val bos = BufferedOutputStream(
            FileOutputStream(
                File(
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(),
                    photoFileName
                )
            )
        )
        bos.write(bytes.toByteArray())
        bos.flush()
        bos.close()
        photosAdapter.submitList(photosList)
        photosAdapter.notifyDataSetChanged()

        updateAttachPhotoVisibility()
    }

    fun saveImage(myBitmap: Bitmap): PhotoUI {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val path: String =
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val name = Calendar.getInstance().getTimeInMillis().toString() + ".jpg"
        val directory = File(path)
        if (!directory.exists())
            directory.mkdirs()

        try {
            val f = File(directory, name)
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                requireContext(),
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"),
                null
            )
            fo.close()
            return PhotoUI(path, name, Uri.parse(path + "/" + name))
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return PhotoUI(path, name, null)
    }

    private fun renderAttachments() {
        photosList.clear()
        if (followerItem.attachments?.size!! > 0) {
            for (i in 0 until followerItem.attachments?.size!!) {
                var byteArray = android.util.Base64.decode(followerItem.attachments!![i], NO_WRAP)
                val photoUI = saveImage(ImageUtils.getBitmapFromByteArray(byteArray)!!)
                photosList.add(photoUI)
//                (rvPhotos.findViewHolderForAdapterPosition(i)!!.itemView.findViewById(R.id.ivImage) as ImageView).setImageBitmap(ImageUtils.getBitmapFromByteArray(byteArray!!))
            }
            updateAttachPhotoVisibility()

            (binding.rvPhotos.adapter as PhotosAdapter).submitList(photosList)
        }
    }

    private fun updateAttachPhotoVisibility() {
        if (photosList.size == 4)
            binding.bAttachPhoto.visibility = View.INVISIBLE
        else
            binding.bAttachPhoto.visibility = View.VISIBLE
    }

    private fun navigateBackWithResult() {
        val intent = Intent()
        val bundle = Bundle()

        if(binding.edReason.text.isNullOrEmpty() || photosList.size == 0) {
            showAlert(getString(R.string.invalid_data))
            return
        }

        try {
            followerItem.attachments!!.clear()
            for (i in 0 until photosList?.size!!) {
                followerItem.attachments!!.add(
                    android.util.Base64.encodeToString(
                        ImageUtils.getByteArrayFromImageView(
                            binding.rvPhotos.findViewHolderForAdapterPosition(i)!!.itemView.findViewById(
                                R.id.ivImage
                            )
                        ), NO_WRAP
                    ).replace("\\", "")
                )
            }
        } catch (e: Exception) {
            showAlert(getString(R.string.invalid_data))
            return
        }

        followerItem.modificationReason = binding.edReason.text.toString()
        followerItem.isDeleted = true
        followerItem.isEdited = false
        bundle.putParcelable("followerItem", followerItem)
        intent.putExtras(bundle)
        parentFragmentManager?.setFragmentResult("bundle", bundle)
        navController().navigateUp()
    }
// endregion

    fun navController() = findNavController()
}
