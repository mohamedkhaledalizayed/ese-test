package com.neqabty.yodawy.modules.address.presentation.view.addaddressscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.yodawy.R
import com.neqabty.yodawy.databinding.ActivityAddAddressBinding
import com.neqabty.yodawy.modules.address.domain.params.AddAddressUseCaseParams
import com.neqabty.yodawy.core.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class AddAddressActivity : BaseActivity<ActivityAddAddressBinding>() {
    private val addAddressViewModel: AddAddressViewModel by viewModels()

    private lateinit var dialog: AlertDialog
override fun getViewBinding() = ActivityAddAddressBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.add_new_address)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please Wait...")
            .build()
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

        dialog.show()
        addAddressViewModel.addAddress(AddAddressUseCaseParams("01090100670",
            binding.nickname.text.toString(),
            binding.street.text.toString(),
            binding.floor.text.toString(),
            binding.building.text.toString(),
            binding.apartment.text.toString(),
            binding.landmark.text.toString()))
        addAddressViewModel.data.observe(this){
            dialog.dismiss()
            finish()
        }

    }
}