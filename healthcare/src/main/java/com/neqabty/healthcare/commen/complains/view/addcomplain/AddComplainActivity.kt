package com.neqabty.healthcare.commen.complains.view.addcomplain

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.complains.view.ComplainsViewModel
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.core.utils.isValidEmail
import com.neqabty.healthcare.databinding.ActivityAddComplainBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog


@AndroidEntryPoint
class AddComplainActivity : BaseActivity<ActivityAddComplainBinding>() {

    private lateinit var loading: AlertDialog
    private val complainsViewModel: ComplainsViewModel by viewModels()
    override fun getViewBinding() = ActivityAddComplainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()
        binding.suggestions.playAnimation()

        binding.mobile.customSelectionActionModeCallback = actionMode
        binding.email.customSelectionActionModeCallback = actionMode
        binding.comment.customSelectionActionModeCallback = actionMode

        if (sharedPreferences.mobile.isNotEmpty()){
            binding.mobile.isEnabled = false
            binding.mobile.setText(sharedPreferences.mobile)
            binding.email.setText(sharedPreferences.email)
        }

        binding.addSuggestion.setOnClickListener {

            if(binding.mobile.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(binding.email.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.enter_email), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!binding.email.text.toString().isValidEmail()){
                Toast.makeText(this, getString(R.string.enter_correct_email), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(binding.comment.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.enter_message), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            complainsViewModel.addComplain(binding.mobile.text.toString(), binding.email.text.toString(), binding.comment.text.toString())
            complainsViewModel.complains.observe(this){
                it?.let { resource ->
                    when (resource.status) {
                        Status.LOADING -> {
                            loading.show()
                        }
                        Status.SUCCESS -> {
                            loading.dismiss()
                            showTicketNumber(resource.data)
                        }
                        Status.ERROR -> {
                            loading.dismiss()
                            Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }

    private fun showTicketNumber(data: String?) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage("تم إرسال طلبك بنجاح رقم الشكوى/المقترح$data")
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()
    }

}