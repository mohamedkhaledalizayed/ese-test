package com.neqabty.healthcare.payment.view.paymentdetails

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.gson.Gson
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants.NATURAL_THERAPY_CODE
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPaymentDetailsBinding
import com.neqabty.healthcare.payment.data.model.*
import com.neqabty.healthcare.payment.data.model.inquiryresponse.DeliveryMethod
import com.neqabty.healthcare.payment.view.PaymentViewModel
import com.neqabty.healthcare.payment.view.paymentmethods.PaymentMethodsActivity
import com.neqabty.healthcare.profile.view.update.UpdateInfoActivity
import com.payment.paymentsdk.integrationmodels.*
import dagger.hilt.android.AndroidEntryPoint
import team.opay.business.cashier.sdk.api.*


@AndroidEntryPoint
class PaymentDetailsActivity : BaseActivity<ActivityPaymentDetailsBinding>(){

    private val paymentViewModel: PaymentViewModel by viewModels()
    private var serviceCode = ""
    private var serviceActionCode = ""
    private var deliveryMethod = ""
    private var deliveryMethodId = 0
    private var deliveryMethodsEnabled = true
    private val mAdapter: DeliveryMethodsAdapter = DeliveryMethodsAdapter()
    private val itemsAdapter: ServicesAdapter = ServicesAdapter()
    override fun getViewBinding() = ActivityPaymentDetailsBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "دفع اشتراك")

        serviceCode = intent.getStringExtra("code")!!
        serviceActionCode = intent.getStringExtra("service_action_code")!!

        binding.itemsRecyclerView.adapter = itemsAdapter
        binding.deliveryMethods.adapter = mAdapter
        mAdapter.onItemClickListener = object : DeliveryMethodsAdapter.OnItemClickListener{
            override fun setOnItemClickListener(item: DeliveryMethod) {
                deliveryMethod = item.type
                deliveryMethodId = item.id
                mAdapter.notifyDataSetChanged()
            }
        }

        if (sharedPreferences.code == NATURAL_THERAPY_CODE) {
            paymentViewModel.getPaymentDetails(serviceCode, serviceActionCode, "12345678")
        } else {
            paymentViewModel.getPaymentDetails(serviceCode, serviceActionCode, sharedPreferences.membershipId)
        }

        paymentViewModel.payment.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE

                        if (resource.data?.receipt == null) {
                            showDialog(getString(R.string.no_reciept))
                        } else if (!resource.data.receipt.status) {
                            Toast.makeText(this@PaymentDetailsActivity, "${resource.data.receipt.error}", Toast.LENGTH_LONG).show()
                        } else {
                            binding.llContent.visibility = View.VISIBLE
                            binding.tvService.text = resource.data.receipt.inquiry_title
                            binding.tvName.text = resource.data.member
                            binding.tvMemberNumber.text = resources.getString(R.string.member_id, sharedPreferences.membershipId)
                            itemsAdapter.submitList(resource.data.receipt.service_data)
                            mAdapter.submitList(resource.data.delivery_methods)
                            binding.total.text = "${resource.data.receipt.net_amount} جنيه"
                            if (resource.data.delivery_methods.isEmpty()){
                                deliveryMethodsEnabled = false
                            }
                        }
                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE

                        if (resource.message.toString().split("#")[0].trim() == "3ak") {

                            val error = Gson().fromJson(
                                resource.message.toString().split("#")[1].trim(),
                                ErrorBody::class.java
                            )

                            when (error.error_key) {
                                4 -> {
                                    showAlertDialogLicence(error.error)
                                }
                                7 -> {
                                    showAlertDialogLicence(error.error)
                                }
                                else -> {
                                    showDialog(error.error)
                                }
                            }

                        }
                    }
                }
            }

        }

        binding.btnNext.setOnClickListener {

            if (deliveryMethodsEnabled && deliveryMethod.isEmpty()){
                Toast.makeText(this@PaymentDetailsActivity, "من فضلك اختر طريقة التوصيل", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val intent = Intent(this, PaymentMethodsActivity::class.java)
            intent.putExtra("data", paymentViewModel.payment.value?.data)
            intent.putExtra("deliveryMethod", deliveryMethod)
            intent.putExtra("deliveryMethodId", deliveryMethodId)
            intent.putExtra("deliveryMethodsEnabled", deliveryMethodsEnabled)
            intent.putExtra("code", serviceCode)
            intent.putExtra("service_action_code", serviceActionCode)
            startActivity(intent)
        }
    }

    private fun showAlertDialogLicence(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.agree)
        ) { dialog, _ ->
            val intent = Intent(this, UpdateInfoActivity::class.java)
            intent.putExtra("key", 100)
            startActivity(intent)
            dialog.dismiss()
            finish()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, resources.getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()

    }

    private fun showDialog(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()

    }

}