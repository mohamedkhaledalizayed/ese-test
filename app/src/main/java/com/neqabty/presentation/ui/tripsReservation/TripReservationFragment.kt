package com.neqabty.presentation.ui.tripsReservation

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.TripDetailsFragmentBinding
import com.neqabty.databinding.TripReservationFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.GovernUI
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.ui.common.CustomImagePagerAdapter
import com.neqabty.presentation.ui.common.PhotosAdapter
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.trip_reservation_fragment.*
import java.io.*
import java.util.*

import javax.inject.Inject

class TripReservationFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TripReservationFragmentBinding>()

    lateinit var tripItem: TripUI

    lateinit var tripReservationViewModel: TripReservationViewModel

    private var adapter by autoCleared<PhotosAdapter>()

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1

    private var captureImage = false

    private var photosList: MutableList<PhotoUI> = mutableListOf<PhotoUI>()

    @Inject
    lateinit var appExecutors: AppExecutors
    var regimentsList: List<TripUI.TripRegiment>? = mutableListOf()
    var roomsList: MutableList<TripUI.TripRoom>? = mutableListOf()
    var childrenList: MutableList<Int>? = mutableListOf()
    var agesList: MutableList<Int>? = mutableListOf()
    var regimentID: Int = 0
    var roomID: Int = 0
    var childrenID: Int = 0

    var reservationRequested = false
    var memberName = ""
    private var isValid = false
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.trip_reservation_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = TripReservationFragmentArgs.fromBundle(arguments!!)
        tripItem = params.tripItem

        tripReservationViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TripReservationViewModel::class.java)

        tripReservationViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        tripReservationViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                tripReservationViewModel.validateUser(PreferencesHelper(requireContext()).user)
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        tripReservationViewModel.validateUser(PreferencesHelper(requireContext()).user)
//        initializeViews()
    }

    private fun handleViewState(state: TripReservationViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.member != null && state.member?.code != 3 && state.member?.code != 4 && !isValid) {
            isValid = true
            memberName = state.member!!.engineerName!!
            initializeViews()
        } else if (state.member?.message != null && !isValid) {
            showMemberValidationAlert(state.member?.message!!)
            state.member?.message = null
        }
        else if (!state.isLoading && isValid && reservationRequested)
            showSuccessAlert()
    }

    fun initializeViews() {
        binding.svContent.visibility = View.VISIBLE
        binding.tripItem = tripItem
        regimentsList = tripItem.regiments
        renderRegiments()
        renderChildrenNumber()

        binding.llChildren.visibility = if (tripItem.regiments?.get(0)?.tripType?.toInt() == 1) View.VISIBLE else View.GONE
        binding.bAttachPhoto.setOnClickListener {
            if (photosList.size < 4)
                addPhoto()
        }


        val adapter = PhotosAdapter(dataBindingComponent, appExecutors) { photo ->
            photosList.remove(photo)
            adapter.notifyDataSetChanged()
        }
        this.adapter = adapter
        binding.rvPhotos.adapter = adapter

        binding.bConfirmReservation.setOnClickListener {
            if (photosList.size > 0) {
                reservationRequested = true
                val prefs = PreferencesHelper(requireContext())
                tripReservationViewModel.bookTrip(prefs.mainSyndicate, PreferencesHelper(requireContext()).user, prefs.mobile, tripItem.regiments?.get(0)?.tripId!!, regimentID, spRegiments.selectedItem.toString(), spRooms.selectedItem.toString(),spChildren.selectedItem.toString().toInt() , spChild1.selectedItem?.toString()+","+spChild2.selectedItem?.toString()+","+spChild3.selectedItem?.toString(), memberName, photosList.size, getPhoto(0), getPhoto(1), getPhoto(2), getPhoto(3))
            } else
                showPickPhotoAlert()
        }
    }

    fun renderRegiments() {
        binding.spRegiments.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, regimentsList)
        binding.spRegiments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                regimentID = (parent.getItemAtPosition(position) as TripUI.TripRegiment).regimentId
                renderRooms()
            }
        }
        binding.spRegiments.setSelection(0)
    }

    fun renderRooms() {
        prepareRooms()
        binding.spRooms.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, roomsList)
        binding.spRooms.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                roomID = (parent.getItemAtPosition(position) as TripUI.TripRoom).roomId
            }
        }
        binding.spRooms.setSelection(0)
    }

    fun prepareRooms() {
        var j = 1
        roomsList?.clear()
        var regiment: TripUI.TripRegiment = regimentsList!![spRegiments.selectedItemPosition]
        if (regiment.hotelOnePerson != 0) {
            roomsList!!.add(TripUI.TripRoom(j, getString(R.string.one_person), regiment.hotelOnePerson))
            j++
        }
        if (regiment.hotelTwoPerson != 0) {
            roomsList!!.add(TripUI.TripRoom(j, getString(R.string.two_persons), regiment.hotelTwoPerson))
            j++
        }
        if (regiment.hotelThreePerson != 0) {
            roomsList!!.add(TripUI.TripRoom(j, getString(R.string.three_persons), regiment.hotelThreePerson))
            j++
        }
        if (regiment.oneRoom != 0) {
            roomsList!!.add(TripUI.TripRoom(j, getString(R.string.one_room), regiment.oneRoom))
            j++
        }
        if (regiment.twoRooms != 0) {
            roomsList!!.add(TripUI.TripRoom(j, getString(R.string.two_rooms), regiment.twoRooms))
            j++
        }
        if (regiment.studio != 0) {
            roomsList!!.add(TripUI.TripRoom(j, getString(R.string.studio), regiment.studio))
            j++
        }
        if (regiment.viewPrice != 0) {
            roomsList!!.add(TripUI.TripRoom(j, getString(R.string.view_price), regiment.viewPrice))
            j++
        }
        if (regiment.sidePrice != 0) {
            roomsList!!.add(TripUI.TripRoom(j, getString(R.string.side_price), regiment.sidePrice))
            j++
        }
    }

    fun renderChildrenNumber() {
        prepareChildrenNumber()
        binding.spChildren.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, childrenList)
        binding.spChildren.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                childrenID = (parent.getItemAtPosition(position) as Int)
                renderChildrenAges()
            }
        }
        binding.spChildren.setSelection(0)
    }

    fun prepareChildrenNumber() {
        childrenList?.clear()
        for (i in 0 until 4) {
            childrenList?.add(i)
        }
    }

    fun renderChildrenAges() {
        prepareChildrenAges()

        binding.tvChild1.visibility = View.GONE
        binding.spChild1.visibility = View.GONE
        binding.tvChild2.visibility = View.GONE
        binding.spChild2.visibility = View.GONE
        binding.tvChild3.visibility = View.GONE
        binding.spChild3.visibility = View.GONE

        when (binding.spChildren.selectedItemId.toInt()) {
            1 -> {
                binding.tvChild1.visibility = View.VISIBLE
                binding.spChild1.visibility = View.VISIBLE
            }
            2 -> {
                binding.tvChild1.visibility = View.VISIBLE
                binding.spChild1.visibility = View.VISIBLE
                binding.tvChild2.visibility = View.VISIBLE
                binding.spChild2.visibility = View.VISIBLE
            }
            3 -> {
                binding.tvChild1.visibility = View.VISIBLE
                binding.spChild1.visibility = View.VISIBLE
                binding.tvChild2.visibility = View.VISIBLE
                binding.spChild2.visibility = View.VISIBLE
                binding.tvChild3.visibility = View.VISIBLE
                binding.spChild3.visibility = View.VISIBLE
            }
        }

        binding.spChild1.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, agesList)
        binding.spChild2.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, agesList)
        binding.spChild3.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, agesList)
//        binding.spChild1.setSelection(0)
    }

    fun prepareChildrenAges() {
        agesList?.clear()
        for (i in 1 until 13) {
            agesList?.add(i)
        }
    }
//region

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
        builder.setMessage(getString(R.string.pick_photo_trips))
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showSuccessAlert() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(getString(R.string.confirm_reservation_msg))
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }
        var dialog = builder?.create()
        dialog?.show()
    }


    private fun showMemberValidationAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.error))
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
            navController().popBackStack()
            navController().navigate(TripReservationFragmentDirections.openLogin(tripItem))
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
// endregion

    fun navController() = findNavController()
}
