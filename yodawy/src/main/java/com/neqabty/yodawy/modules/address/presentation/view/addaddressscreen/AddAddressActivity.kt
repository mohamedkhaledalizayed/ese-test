package com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.ActivityAddAddressBinding
import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.modules.address.presentation.view.adressscreen.AddressViewModel
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAddressBinding

    private val addAddressViewModel: AddAddressViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_address)



        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.add_new_address)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
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

        addAddressViewModel.addAddress(AddAddressUseCaseParams("01090100670",
            "home",
            binding.street.text.toString(),
            binding.floor.text.toString(),
            binding.building.text.toString(),
            binding.apartment.text.toString(),
            binding.landmark.text.toString()))
        addAddressViewModel.data.observe(this){
            finish()
        }

    }
}