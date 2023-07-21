package com.neqabty.healthcare.chefaa.address.presentation.view.addaddressscreen

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.LATITUDE
import com.neqabty.healthcare.core.data.Constants.LONGITUDE
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.CehfaaActivityAddAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class AddAddressActivity : BaseActivity<CehfaaActivityAddAddressBinding>() {
    private val addAddressViewModel: AddAddressViewModel by viewModels()

    private var latitude = 0.0
    private var longitude = 0.0
    var district = ""
    var city = ""
    var gov = ""
    private var buildingType = "المنزل"
    private lateinit var dialog: AlertDialog
    override fun getViewBinding() = CehfaaActivityAddAddressBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.add_new_address)

        binding.headerContainer.setOnClickListener { finish() }
        binding.street.customSelectionActionModeCallback = actionMode
        binding.building.customSelectionActionModeCallback = actionMode
        binding.floor.customSelectionActionModeCallback = actionMode
        binding.apartment.customSelectionActionModeCallback = actionMode
        binding.landmark.customSelectionActionModeCallback = actionMode


        latitude = intent.getDoubleExtra(LATITUDE, 0.0)
        longitude = intent.getDoubleExtra(LONGITUDE, 0.0)
        district = intent.getStringExtra("district")!!
        city = intent.getStringExtra("city")!!
        gov = intent.getStringExtra("gov")!!
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        binding.street.setText("${district}, ${city}, ${gov}")

        binding.homeLayout.setOnClickListener {
            buildingType = "المنزل"
            binding.homeLayout.background = resources.getDrawable(R.drawable.medicine_bg)
            binding.workLayout.background = resources.getDrawable(R.drawable.address_bg)

            binding.homeIcon.setImageResource(R.drawable.home_white)
            binding.workIcon.setImageResource(R.drawable.portfolio_black)


            binding.home.setTextColor(Color.WHITE)
            binding.work.setTextColor(Color.BLACK)
        }

        binding.workLayout.setOnClickListener {
            buildingType = "العمل"
            binding.homeLayout.background = resources.getDrawable(R.drawable.address_bg)
            binding.workLayout.background = resources.getDrawable(R.drawable.medicine_bg)

            binding.homeIcon.setImageResource(R.drawable.home_black)
            binding.workIcon.setImageResource(R.drawable.portfolio_white)

            binding.home.setTextColor(Color.BLACK)
            binding.work.setTextColor(Color.WHITE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveAddress(view: View) {
        if (binding.street.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.add_street_name), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.building.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.add_building_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.floor.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.add_floor_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.apartment.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.add_apartment_number), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.landmark.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.nearest_landmark), Toast.LENGTH_LONG).show()
            return
        }

        if (binding.landmark.text.toString().isNullOrEmpty()){
            Toast.makeText(this, getString(R.string.nickname_), Toast.LENGTH_LONG).show()
            return
        }

        if (latitude == 0.0 || longitude == 0.0 ){
            Toast.makeText(this, getString(R.string.location), Toast.LENGTH_LONG).show()
            return
        }

        addAddressViewModel.addAddress(
            phone = Constants.mobileNumber,
            title = buildingType,
            streetName= binding.street.text.toString(),
            floor = binding.floor.text.toString(),
            buildingNo = binding.building.text.toString(),
            apartment = binding.apartment.text.toString(),
            lat = latitude.toString() ,
            long = longitude.toString(),
            landMark =binding.landmark.text.toString())

        addAddressViewModel.data.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING->{
                        dialog.show()
                    }
                    Status.SUCCESS->{
                        Toast.makeText(this, "تم إضافة العنوان بنجاح.", Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                        finish()
                    }
                    Status.ERROR->{
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                        dialog.dismiss()
                    }
                }
            }
        }

    }

}