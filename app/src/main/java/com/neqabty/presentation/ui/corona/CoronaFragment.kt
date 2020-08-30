package com.neqabty.presentation.ui.corona

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
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.CoronaFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.ui.common.PhotosAdapter
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.corona_fragment.*
import java.io.*
import javax.inject.Inject

class CoronaFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<CoronaFragmentBinding>()

    @Inject
    lateinit var coronaViewModel: CoronaViewModel

    private var photosAdapter by autoCleared<PhotosAdapter>()
    private val REQUEST_CAMERA = 0
    private var photosList: MutableList<PhotoUI> = mutableListOf<PhotoUI>()
    private var captureImage = false

    @Inject
    lateinit var appExecutors: AppExecutors

    var isSubmitted: Boolean = false
    lateinit var coronasTypesList: List<String>
    var selectedType: String = ""
    var selectedSyndicate: Int = 0
    //    var isSubmitted: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.corona_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        coronaViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CoronaViewModel::class.java)

        coronaViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        coronaViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE

                coronaViewModel.getSyndicates()
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            })
        })
    }

    private fun initializeViews() {
        edName.setText(PreferencesHelper(requireContext()).name)
        edMemberNumber.setText(PreferencesHelper(requireContext()).user)
        edMobileNumber.setText(PreferencesHelper(requireContext()).mobile)
        renderSyndicates()
        renderTypes()

        binding.bAddPhotos1.setOnClickListener {
            if (photosList.size < 4)
                grantCameraPermission()
        }

        binding.bAddPhotos2.setOnClickListener {
            if (photosList.size < 4)
                grantCameraPermission()
        }
        binding.bSubmit.setOnClickListener {
            if (edMemberNumber.text.toString().isNullOrBlank() || edJob.text.isNullOrBlank() ||
                    edWork.text.isNullOrBlank() ||
                    edMedication.text.isNullOrBlank() && edMedication.visibility == View.VISIBLE ||
                    edQuarantine.text.isNullOrBlank() && edQuarantine.visibility == View.VISIBLE ||
                    edFamily.text.isNullOrBlank() && edFamily.visibility == View.VISIBLE ||
                    edDamageType.text.isNullOrBlank() && edDamageType.visibility == View.VISIBLE) {
                showInvalidDataAlert()
            } else if (photosList.size > 0) {
                isSubmitted = true
                coronaViewModel.createRequest(edMemberNumber.text.toString(),
                        PreferencesHelper(requireContext()).mobile,
                        selectedSyndicate,
                        PreferencesHelper(requireContext()).name,
                        spTypes.selectedItem as String,
                        edJob.text.toString(),
                        edWork.text.toString(),
                        edMedication.text.toString(),
                        edQuarantine.text.toString(),
                        if (edFamily.text.toString().isNotBlank()) edFamily.text.toString().toInt() else 0,
                        edDamageType.text.toString(),
                        photosList.size, getPhoto(0), getPhoto(1), getPhoto(2), getPhoto(3), getPhoto(4))
            } else
                showPickPhotoAlert()
        }

        val adapter = PhotosAdapter(dataBindingComponent, appExecutors) { photo ->
            photosList.remove(photo)
            photosAdapter.notifyDataSetChanged()
        }
        this.photosAdapter = adapter
        binding.rvPhotos.adapter = adapter
    }

    private fun handleViewState(state: CoronaViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading && state.message.isNotBlank() && isSubmitted) {
            isSubmitted = false
            showSuccessAlert()
        } else if (!state.isLoading && state.syndicates != null) {
            binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            initializeViews()
        }
    }

    //region
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA) {
            grantCameraPermission()
        }
    }

    fun renderSyndicates() {
        binding.spSyndicates.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, coronaViewModel.viewState.value!!.syndicates!!)
        binding.spSyndicates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedSyndicate = (parent.getItemAtPosition(position) as SyndicateUI).id
            }
        }
        binding.spSyndicates.setSelection(0)
    }

    fun renderTypes() {
        coronasTypesList = mutableListOf(getString(R.string.corona_type_one), getString(R.string.corona_type_two))
        binding.spTypes.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, coronasTypesList)
        binding.spTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedType = parent.getItemAtPosition(position) as String
                binding.type = position + 1
            }
        }
        binding.spTypes.setSelection(0)
    }

    fun showSuccessAlert() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(getString(R.string.corona_sent))
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().navigateUp()
        }
        var dialog = builder?.create()
        dialog?.show()
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

    private fun onCaptureImageResult(data: Intent) {
        var thumbnail: Bitmap = data.getExtras()!!.get("data") as Bitmap
        var bytes: ByteArrayOutputStream = ByteArrayOutputStream()
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

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
        photosAdapter.submitList(photosList)
        photosAdapter.notifyDataSetChanged()
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
        builder.setMessage(getString(R.string.pick_photo_trips))
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showInvalidDataAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.invalid_data))
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

// endregion

    fun navController() = findNavController()
}
