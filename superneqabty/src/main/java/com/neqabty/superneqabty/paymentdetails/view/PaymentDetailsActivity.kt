package com.neqabty.superneqabty.paymentdetails.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.core.ui.BaseActivity
import com.neqabty.superneqabty.core.utils.Constants
import com.neqabty.superneqabty.databinding.ActivityPaymentDetailsBinding
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.neqabty.superneqabty.home.view.homescreen.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import me.cowpay.PaymentMethodsActivity
import me.cowpay.util.CowpayConstantKeys
import team.opay.business.cashier.sdk.api.PayInput
import team.opay.business.cashier.sdk.api.PaymentStatus
import team.opay.business.cashier.sdk.api.Status
import team.opay.business.cashier.sdk.api.WebJsResponse
import team.opay.business.cashier.sdk.pay.PaymentTask

@AndroidEntryPoint
class PaymentDetailsActivity : BaseActivity<ActivityPaymentDetailsBinding>() {

    private val mAdapter = ItemsAdapter()
    private val paymentDetailsViewModel: PaymentDetailsViewModel by viewModels()
    override fun getViewBinding() = ActivityPaymentDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.payments)

        binding.rvDetails.adapter = mAdapter
        paymentDetailsViewModel.getPaymentDetails("s0005","2718")
        paymentDetailsViewModel.payment.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.superneqabty.core.utils.Status.LOADING -> {

                    }
                    com.neqabty.superneqabty.core.utils.Status.SUCCESS -> {
                        binding.tvService.text = resource.data?.service?.name
                        binding.tvName.text = resource.data?.member?.account?.fullname
                        binding.tvMemberNumber.text = ""
                        binding.tvAmount.text = resource.data?.receipt?.totalPrice.toString()
                        mAdapter.submitList(resource.data?.receipt?.details)
                    }
                    com.neqabty.superneqabty.core.utils.Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        binding.rbCard.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.ivCard.visibility = View.VISIBLE
                binding.llChannels.visibility = View.GONE
                binding.ivFawry.visibility = View.GONE
            }
        }
        binding.rbChannel.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.llChannels.visibility = View.VISIBLE
                binding.ivCard.visibility = View.GONE
                binding.ivFawry.visibility = View.GONE
            }
        }
        binding.rbFawry.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.ivFawry.visibility = View.VISIBLE
                binding.llChannels.visibility = View.GONE
                binding.ivCard.visibility = View.GONE
            }
        }

        binding.tvChannels.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://cashier.opaycheckout.com/map")))
        }

    }


    fun cowPayPayment(isCredit: Boolean) {
        var intent = Intent(this, PaymentMethodsActivity::class.java)

        var PaymentMethod = ArrayList<String>()
        if (isCredit)
            PaymentMethod.add(CowpayConstantKeys.CreditCardMethod)
        else
            PaymentMethod.add(CowpayConstantKeys.FawryMethod)
        intent.putExtra(CowpayConstantKeys.PaymentMethod, PaymentMethod)

        intent.putExtra(CowpayConstantKeys.AuthorizationToken, Constants.cowpayAuthToken)

        //set environment production or sandBox
        //CowpayConstantKeys.Production or CowpayConstantKeys.SandBox
        intent.putExtra(CowpayConstantKeys.PaymentEnvironment, Constants.COWPAY_MODE)
        //set locale language
        intent.putExtra(CowpayConstantKeys.Language, CowpayConstantKeys.ENGLISH)
        // use pay with credit card
        intent.putExtra(
            CowpayConstantKeys.CreditCardMethodType,
            CowpayConstantKeys.CreditCardMethodPay
        )

        intent.putExtra(CowpayConstantKeys.MerchantCode, "3GpZbdrsnOrT")
        intent.putExtra(
            CowpayConstantKeys.MerchantHashKey,
            "\$2y\$10$" + "gqYaIfeqefxI162R6NipSucIwvhO9pbksOf0.OP76CVMZEYBPQlha"
        )
        //order id
        intent.putExtra(CowpayConstantKeys.MerchantReferenceId, "anroid_1")
        //order price780
        intent.putExtra(CowpayConstantKeys.Amount, "10")
        //user data
        intent.putExtra(CowpayConstantKeys.Description, "9")
        intent.putExtra(CowpayConstantKeys.CustomerName, sharedPreferences.name)
        intent.putExtra(CowpayConstantKeys.CustomerMobile, sharedPreferences.mobile)
        intent.putExtra(CowpayConstantKeys.CustomerEmail, "customer@customer.com")
        //user id
        intent.putExtra(CowpayConstantKeys.CustomerMerchantProfileId, "anroid_1")


        startActivityForResult(intent, CowpayConstantKeys.PaymentMethodsActivityRequestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PaymentTask.REQUEST_PAYMENT){
            if (resultCode == PaymentTask.RESULT_PAYMENT){
                val response = data?.getSerializableExtra(PaymentTask.RESPONSE_DATA) as WebJsResponse?
                handlePaymentResponse(response)
            }
        }
        if (requestCode == CowpayConstantKeys.PaymentMethodsActivityRequestCode && data != null && resultCode == Activity.RESULT_OK) {
            var responseCode = data.extras!!.getInt(CowpayConstantKeys.ResponseCode, 0)

            if (responseCode == CowpayConstantKeys.ErrorCode) {
                showAlert(getString(R.string.payment_canceled)) {
//                    navController().popBackStack()
//                    navController().navigate(R.id.homeFragment)
                }
            } else if (responseCode == CowpayConstantKeys.SuccessCode) {
                var responseMSG = data.extras!!.getString(CowpayConstantKeys.ResponseMessage)
                var PaymentGatewayReferenceId =
                    data.extras!!.getString(CowpayConstantKeys.PaymentGatewayReferenceId)
                responseMSG?.let {
                    showAlert(if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) getString(R.string.payment_successful) + PaymentGatewayReferenceId else getString(R.string.payment_reference) + PaymentGatewayReferenceId) {
//                        navController().popBackStack()
//                        navController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
    }


    private fun handleViewState() {
//        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card)
            oPayPayment(true)
        else if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
            oPayPayment(false)
        else
            cowPayPayment(false)
    }


    fun oPayPayment(isCredit: Boolean) {
        val paymentType = if(isCredit) "BankCard" else "ReferenceCode"
        PaymentTask.sandBox = Constants.OPAY_MODE
        val payInput = PayInput(
            publickey = Constants.OPAY_PUBLIC_KEY,
            merchantId = Constants.OPAY_MERCHANT_ID,
            merchantName = Constants.OPAY_MERCHANT_NAME,
            reference = "anroid_1",
            countryCode = "EG", // uppercase
            currency = "EGP", // uppercase
            payAmount = (10 * 100).toLong(),
            productName = "annualSubscription",
            productDescription = "android_" + 9,
            callbackUrl = Constants.OPAY_PAYMENT_CALLBACK_URL,
            userClientIP = "110.246.160.183",
            expireAt = if(isCredit) 30 else 2880,
            paymentType = paymentType // optional
        )

//        llSuperProgressbar.visibility = View.VISIBLE
        PaymentTask(this).createOrder(payInput, callback = { status, response ->
//            llSuperProgressbar.visibility = View.GONE
            when (status) {
                Status.ERROR -> { // error
                    Toast.makeText(this , response.message,
                        Toast.LENGTH_SHORT).show()
                } else -> {
            }
            }
        })
    }

    private fun handlePaymentResponse(response: WebJsResponse?) {
        when (response?.orderStatus) {
            PaymentStatus.INITIAL -> {
            }
            PaymentStatus.PENDING -> {
                if (response.eventName.equals("clickResultOKBtn") && binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_channel)
                    showAlert(getString(R.string.payment_reference_without_code)) {
//                        navController().popBackStack()
//                        navController().navigate(R.id.homeFragment)
                    }
            }
            PaymentStatus.SUCCESS -> {
                showAlert(
                    if (binding.rgPaymentMechanismType.checkedRadioButtonId == R.id.rb_card) getString(
                        R.string.payment_successful
                    ) + response.orderNo
                    else getString(R.string.payment_reference) + response.referenceCode
                ) {
//                    navController().popBackStack()
//                    navController().navigate(R.id.homeFragment)
                }
            }
            PaymentStatus.FAIL -> {
                showAlert(getString(R.string.payment_canceled)) {
//                    navController().popBackStack()
//                    navController().navigate(R.id.homeFragment)
                }
            }
            PaymentStatus.CLOSE -> {
                showAlert(getString(R.string.payment_canceled)) {
//                    navController().popBackStack()
//                    navController().navigate(R.id.homeFragment)
                }
            }
        }
    }

}