package com.neqabty.presentation.ui.tripsReservation

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
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.TripReservationFragmentBinding
import com.neqabty.domain.entities.PersonEntity
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.PhotoUI
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.ui.addCompanion.AddCompanionFragment
import com.neqabty.presentation.ui.common.PhotosAdapter
import com.neqabty.presentation.util.DisplayMetrics
import com.neqabty.presentation.util.ImageUtils
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.medical_main_fragment.*
import kotlinx.android.synthetic.main.trip_reservation_fragment.*
import java.io.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TripReservationFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<TripReservationFragmentBinding>()

    lateinit var tripItem: TripUI

    private val tripReservationViewModel: TripReservationViewModel by viewModels()

    private var photosAdapter by autoCleared<PhotosAdapter>()
    private var companionsAdapter by autoCleared<CompanionsAdapter>()

    private val REQUEST_CAMERA = 0
    private val SELECT_FILE = 1
    private val ADD_COMPANION = 2

    private var captureImage = false
    private var PhotoFileName = ""
    lateinit var photoFileURI: Uri

    private var photosList: MutableList<PhotoUI> = mutableListOf<PhotoUI>()
    private var companionsList: MutableList<PersonEntity> = mutableListOf<PersonEntity>()

    @Inject
    lateinit var appExecutors: AppExecutors
    var regimentsList: List<TripUI.TripRegiment>? = mutableListOf()
    var roomsList: MutableList<TripUI.TripRoom>? = mutableListOf()
    var childrenList: MutableList<Int>? = mutableListOf()
    var agesList: MutableList<Int>? = mutableListOf()
    lateinit var regiment: TripUI.TripRegiment
    var roomID: Int = 0
    var childrenID: Int = 0

    var reservationRequested = false
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

        val params = TripReservationFragmentArgs.fromBundle(requireArguments())
        tripItem = params.tripItem

        tripReservationViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        tripReservationViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
//                tripReservationViewModel.paymentInquiry(sharedPref.user)
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })
//        tripReservationViewModel.paymentInquiry(sharedPref.user)
        initializeViews()
    }

    private fun handleViewState(state: TripReservationViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
//        if (state.member != null && state.member?.code != 3 && state.member?.code != 4 && !isValid) {
//            isValid = true
//            memberName = state.member!!.engineerName!!
//            initializeViews()
//        } else if (state.member?.message != null && !isValid) {
//            showMemberValidationAlert(state.member?.message!!)
//            state.member?.message = null
//        }
//        else if (!state.isLoading && isValid && reservationRequested)
//            showSuccessAlert()

//        if (state.member != null && state.member?.amount?.toInt() == 0 && !isValid) {
//            isValid = true
//            memberName = state.member!!.engineerName!!
//            initializeViews()
//        } else if (state.member?.message != null && !isValid) {
//            showMemberValidationAlert(getString(R.string.user_not_allowed))
//            state.member?.message = null
//        }
//        else
        if (!state.isLoading && reservationRequested)
            showSuccessAlert()
    }

    fun initializeViews() {
        binding.svContent.visibility = View.VISIBLE
        binding.tripItem = tripItem
        regimentsList = tripItem.regiments
        renderRegiments()
        renderChildrenNumber()

//        binding.llChildren.visibility = if (tripItem.regiments?.get(0)?.tripType?.toInt() == 1) View.VISIBLE else View.GONE
        binding.bAttachCompanion.setOnClickListener {
            openAddCompanionFragment()
        }

        binding.bAttachPhoto.setOnClickListener {
            if (photosList.size < 10)
                grantCameraPermission()
        }

        val adapter = PhotosAdapter(dataBindingComponent, appExecutors) { photo ->
            photosList.remove(photo)
            photosAdapter.notifyDataSetChanged()
            if(photosList.size < 10)
                bAttachPhoto.visibility = View.VISIBLE
        }
        this.photosAdapter = adapter
        binding.rvPhotos.adapter = adapter

        companionsAdapter = CompanionsAdapter(dataBindingComponent, appExecutors) { companion ->
            companionsList.remove(companion)
            companionsAdapter.notifyDataSetChanged()
        }
        binding.rvCompanions.adapter = companionsAdapter

        binding.bConfirmReservation.setOnClickListener {
            if (photosList.size > 0) {
                reservationRequested = true
                tripReservationViewModel.bookTrip(sharedPref.mainSyndicate, sharedPref.user, sharedPref.mobile, tripItem.regiments?.get(0)?.tripId!!, regiment.regimentId, regiment.toString() , roomID.toString(), spChildren.selectedItem.toString().toInt(), spChild1.selectedItem?.toString() + "," + spChild2.selectedItem?.toString() + "," + spChild3.selectedItem?.toString(), sharedPref.name, companionsList.toList(), photosList.size, companionsList.size,
                        getPhoto(0), getPhoto(1), getPhoto(2), getPhoto(3),
                        getPhoto(4), getPhoto(5), getPhoto(6), getPhoto(7),
                        getPhoto(8), getPhoto(9))
            } else
                showPickPhotoAlert()
        }
    }

    fun renderRegiments() {
        for (i in 0 until regimentsList?.size!!) {
            binding.tlRegiments.addTab(binding.tlRegiments.newTab().setText(regimentsList!![i].dateFrom + " :" + regimentsList!![i].dateTo))
            val headerView: View = LayoutInflater.from(context).inflate(R.layout.tab_regiment_item, null, false)
            headerView.findViewById<TextView>(R.id.tvRegiment).setText(getString(R.string.regiment) + " " + (i+1) )
            headerView.findViewById<TextView>(R.id.tvDateFrom).setText(Html.fromHtml(getString(R.string.date_from, regimentsList!![i].dateFrom)))
            headerView.findViewById<TextView>(R.id.tvDateTo).setText(Html.fromHtml(getString(R.string.date_to, regimentsList!![i].dateTo)))
            binding.tlRegiments.getTabAt(i)?.setCustomView(headerView.rootView)
        }

        tlRegiments.requestLayout()

        val layoutParams: ViewGroup.LayoutParams = tlRegiments.layoutParams
        layoutParams.height = DisplayMetrics.width * 35 / 100
        tlRegiments.layoutParams = layoutParams

        tlRegiments!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                regiment = regimentsList!![tab.position]
                renderRooms()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }


            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        binding.tlRegiments.getTabAt(regimentsList!!.size-1)!!.select()
        binding.tlRegiments.getTabAt(0)!!.select()
    }

    fun renderRooms() {
        prepareRooms()
        binding.tlRoomType.removeAllTabs()

        for (i in 0 until roomsList?.size!!) {
            binding.tlRoomType.addTab(binding.tlRoomType.newTab().setText(roomsList!![i].name))
            val headerView: View = LayoutInflater.from(context).inflate(R.layout.tab_room_item, null, false)
            headerView.findViewById<TextView>(R.id.tvText).setText(roomsList!![i].name)
            headerView.findViewById<TextView>(R.id.tvPrice).setText(Html.fromHtml(getString(R.string.currency, roomsList!![i].price.toString())))
            binding.tlRoomType.getTabAt(i)?.setCustomView(headerView.rootView)
        }

        tlRoomType.requestLayout()

        val layoutParams: ViewGroup.LayoutParams = tlRoomType.layoutParams
        layoutParams.height = DisplayMetrics.width * 20 / 100
        tlRoomType.layoutParams = layoutParams

        tlRoomType!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                roomID = roomsList!![tab.position].roomId
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        tlRoomType.getTabAt(0)?.select()
    }

    fun prepareRooms() {
        var j = 1
        roomsList?.clear()
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
        binding.spChildren.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, childrenList!!)
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

        binding.spChild1.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, agesList!!)
        binding.spChild2.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, agesList!!)
        binding.spChild3.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, agesList!!)
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
                onCaptureImageResult()
            else if (requestCode == ADD_COMPANION) {
                addCompanion(data!!)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA) {
            grantCameraPermission()
        }
    }

    private fun openAddCompanionFragment() {
        val fragmentManager = this@TripReservationFragment.fragmentManager
        val addCompanionFragment = AddCompanionFragment()
        addCompanionFragment.setTargetFragment(this, ADD_COMPANION)
        addCompanionFragment.show(requireFragmentManager(), "name")
    }

    private fun addCompanion(data: Intent) {
        val companion = data.extras?.getParcelable<PersonEntity>("companion")
        companionsList.add(companion as PersonEntity)
        companionsAdapter.submitList(companionsList)
        companionsAdapter.notifyDataSetChanged()
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
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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
                photosAdapter.submitList(photosList)
                photosAdapter.notifyDataSetChanged()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun onCaptureImageResult() {
        photosList.add(PhotoUI(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), PhotoFileName, photoFileURI))
        val bitmap: Bitmap = BitmapFactory.decodeFile(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + "/" + PhotoFileName)
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bytes)
        val bos = BufferedOutputStream(FileOutputStream(File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString(), PhotoFileName)))
        bos.write(bytes.toByteArray())
        bos.flush()
        bos.close()
        photosAdapter.submitList(photosList)
        photosAdapter.notifyDataSetChanged()

        if(photosList.size == 10)
            bAttachPhoto.visibility = View.GONE
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
            navController().navigate(TripReservationFragmentDirections.openLogin(2))
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
// endregion

    fun navController() = findNavController()
}
