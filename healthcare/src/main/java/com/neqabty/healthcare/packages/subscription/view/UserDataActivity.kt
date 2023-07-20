package com.neqabty.healthcare.packages.subscription.view


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityUserDataBinding
import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class UserDataActivity : BaseActivity<ActivityUserDataBinding>() {

    private lateinit var datePicker: DatePickerDialog
    private lateinit var calendar: Calendar
    override fun getViewBinding() = ActivityUserDataBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.subscription)
        val packageDetails = intent.extras?.getParcelable<PackagesEntity>("package")!!
        binding.ccp.registerCarrierNumberEditText(binding.deliveryPhone)


        binding.etName.setText(sharedPreferences.name)
        if (!sharedPreferences.nationalId.isNullOrEmpty()) {
            binding.etNationalId.setText(sharedPreferences.nationalId)
        }

        binding.etEmail.setText(sharedPreferences.email)
        binding.etEmail.isEnabled = false

        calendar = Calendar.getInstance()


        binding.etBirthDate.setOnClickListener {
            datePicker = DatePickerDialog(
                this@UserDataActivity,
                { _, year, monthOfYear, dayOfMonth ->
                    binding.etBirthDate.text = "$year-${monthOfYear + 1}-$dayOfMonth"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()
        }

        binding.nextBtn.setOnClickListener {

            if (binding.etName.text.toString().isEmpty()) {
                Toast.makeText(this, "من فضلك ادخل الاسم.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.etNationalId.text.toString().isEmpty()) {
                Toast.makeText(this, "من فضلك ادخل الرقم القومى", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.etNationalId.text.toString().length < 14) {
                Toast.makeText(this, "من فضلك ادخل الرقم القومى بشكل صحيح.", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            if (binding.etEmail.text.toString().isEmpty()) {
                Toast.makeText(this, "من فضلك ادخل البريد الالكترونى.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.etBirthDate.text.toString().isEmpty()) {
                Toast.makeText(this, "من فضلك ادخل تاريخ الميلاد.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!binding.ccp.isValidFullNumber) {
                Toast.makeText(this, "رقم الهاتف غير صالح", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.etAddress.text.toString().isEmpty()) {
                Toast.makeText(this, "من فضلك ادخل العنوان.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.etJob.text.toString().isEmpty()) {
                Toast.makeText(this, "من فضلك ادخل الوظيفة.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val intent = Intent(this, SubscriptionActivity::class.java)
            intent.putExtra("name", binding.etName.text.toString())
            intent.putExtra("nationalId", binding.etNationalId.text.toString())
            intent.putExtra("email", binding.etEmail.text.toString())
            intent.putExtra("birthDate", binding.etBirthDate.text.toString())
            intent.putExtra("deliveryPhone", binding.ccp.fullNumberWithPlus)
            intent.putExtra("address", binding.etAddress.text.toString())
            intent.putExtra("job", binding.etJob.text.toString())
            intent.putExtra("package", packageDetails)
            startActivity(intent)
        }

        binding.etName.customSelectionActionModeCallback = actionMode
        binding.etNationalId.customSelectionActionModeCallback = actionMode
        binding.etEmail.customSelectionActionModeCallback = actionMode
        binding.etAddress.customSelectionActionModeCallback = actionMode
        binding.etJob.customSelectionActionModeCallback = actionMode
    }





}