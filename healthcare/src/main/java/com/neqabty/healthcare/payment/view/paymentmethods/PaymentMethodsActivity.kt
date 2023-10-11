package com.neqabty.healthcare.payment.view.paymentmethods


import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.otp.view.VerifyPhoneActivity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.AGRI_CODE
import com.neqabty.healthcare.core.data.Constants.MORSHEDIN_CODE
import com.neqabty.healthcare.core.data.Constants.VAT_CODE
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityPaymentMethodsBinding
import com.neqabty.healthcare.payment.data.model.PaymentAgriBody
import com.neqabty.healthcare.payment.data.model.PaymentMorshedinBody
import com.neqabty.healthcare.payment.data.model.PaymentNaturalBody
import com.neqabty.healthcare.payment.data.model.PaymentVatBody
import com.neqabty.healthcare.payment.data.model.inquiryresponse.GatewaysData
import com.neqabty.healthcare.payment.data.model.inquiryresponse.InquiryModel
import com.neqabty.healthcare.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.payment.view.PaymentViewModel
import com.neqabty.healthcare.payment.view.paymentstatus.PaymentStatusActivity
import com.payment.paymentsdk.integrationmodels.PaymentSdkError
import com.payment.paymentsdk.integrationmodels.PaymentSdkTransactionDetails
import com.payment.paymentsdk.sharedclasses.interfaces.CallbackPaymentInterface
import dagger.hilt.android.AndroidEntryPoint
import team.opay.business.cashier.sdk.api.PayInput
import team.opay.business.cashier.sdk.api.PaymentStatus
import team.opay.business.cashier.sdk.api.Status
import team.opay.business.cashier.sdk.api.WebJsResponse
import team.opay.business.cashier.sdk.pay.PaymentTask

@AndroidEntryPoint
class PaymentMethodsActivity : BaseActivity<ActivityPaymentMethodsBinding>(), CallbackPaymentInterface {

    private var paymentMethod = ""
    private var serviceCode = ""
    private var serviceActionCode = ""
    private var deliveryMethod = ""
    private var deliveryMethodId = 0
    private val branchesAdapter = BranchesAdapter()
    private var entityBranch = ""
    private var deliveryMethodsEnabled = false
    private var branchesList: List<BranchesEntity>? = null
    private val paymentViewModel: PaymentViewModel by viewModels()
    private lateinit var data: InquiryModel
    override fun getViewBinding() = ActivityPaymentMethodsBinding.inflate(layoutInflater)
    private val mAdapter: MegaPaymentMethodsAdapter = MegaPaymentMethodsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "دفع اشتراك")

        serviceCode = intent.getStringExtra("code")!!
        serviceActionCode = intent.getStringExtra("service_action_code")!!
        deliveryMethod = intent.getStringExtra("deliveryMethod") ?: ""
        deliveryMethodId = intent.getIntExtra("deliveryMethodId", 0)
        deliveryMethodsEnabled = intent.getBooleanExtra("deliveryMethodsEnabled", false)
        data = intent.extras?.getParcelable<InquiryModel>("data")!!
        mAdapter.submitList(data.gateways_data)

        when (deliveryMethod) {
            "Home" -> {
                binding.spinnerBranchesContainer.visibility = View.GONE
            }
            "Branch" -> {
                binding.address.visibility = View.GONE
                paymentViewModel.getBranches()
            }
            else -> {
                binding.spinnerBranchesContainer.visibility = View.GONE
            }
        }

        binding.paymentMethods.adapter = mAdapter
        mAdapter.onItemClickListener = object : MegaPaymentMethodsAdapter.OnItemClickListener{
            override fun setOnItemClickListener(item: GatewaysData) {
                paymentMethod = item.name
                mAdapter.notifyDataSetChanged()
            }
        }

        paymentViewModel.branches.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        branchesList = resource.data
                        branchesAdapter.submitList(
                            resource.data!!.toMutableList()
                                .also { list ->
                                    list.add(
                                        0,
                                        BranchesEntity(
                                            id = "",
                                            address = "",
                                            entity = null,
                                            city = resources.getString(R.string.select_branch)
                                        )
                                    )
                                })
                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }

        binding.spBranches.adapter = branchesAdapter
        binding.spBranches.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (branchesList != null && i != 0) {
                    entityBranch = branchesList?.get(i - 1)!!.id
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        paymentViewModel.paymentInfo.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    com.neqabty.healthcare.core.utils.Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    com.neqabty.healthcare.core.utils.Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        when (resource.data?.paymentMethod) {
                            "Opay Card" -> {
                                oPayPayment(resource.data)
                            }
                            "Opay Code" -> {
                                showAlertDialog(resource.data.paymentGatewayTransactionNum)
                            }
                            "Fawry Code" -> {
                                showAlertDialog(resource.data.paymentGatewayTransactionNum)
                            }
                            else -> {

                            }
                        }
                    }
                    com.neqabty.healthcare.core.utils.Status.ERROR -> {
                        binding.btnNext.isEnabled = true
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }

        }

        binding.btnNext.setOnClickListener {

            if (paymentMethod.isEmpty()){
                Toast.makeText(this, "من فضلك اختر طريقة الدفع.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (deliveryMethod == "Home" && binding.address.text.toString().isEmpty()){
                Toast.makeText(this@PaymentMethodsActivity, "من فضلك ادخل العنوان.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (deliveryMethod == "Branch" && entityBranch.isEmpty()){
                Toast.makeText(this@PaymentMethodsActivity, "من فضلك اختر الفرع.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (sharedPreferences.isPhoneVerified) {
                binding.btnNext.isEnabled = false

                when (sharedPreferences.code) {
                    Constants.NATURAL_THERAPY_CODE -> {
                        paymentViewModel.getPaymentInfo(
                            PaymentNaturalBody(
                                serviceCode = serviceCode,
                                serviceActionCode = serviceActionCode,
                                paymentMethod = paymentMethod,
                                address = binding.address.text.toString(),
                                deliveryMethod = if (deliveryMethodsEnabled) deliveryMethodId else null,
                                deliveryMobile = binding.mobile.text.toString(),
                                deliveryNotes = binding.notes.text.toString()
                            )
                        )
                    }
                    AGRI_CODE -> {
                        paymentViewModel.getPaymentInfo(
                            PaymentAgriBody(
                                serviceCode = serviceCode,
                                serviceActionCode = serviceActionCode,
                                paymentMethod = paymentMethod,
                                deliveryMethod = if (deliveryMethodsEnabled) deliveryMethodId else null,
                                membershipId = sharedPreferences.membershipId.toInt(),
                                deliveryMobile = binding.mobile.text.toString(),
                                deliveryNotes = binding.notes.text.toString()
                            )
                        )
                    }
                    VAT_CODE -> {
                        paymentViewModel.getPaymentInfo(
                            PaymentVatBody(
                                serviceCode = serviceCode,
                                serviceActionCode = serviceActionCode,
                                paymentMethod = paymentMethod,
                                address = binding.address.text.toString(),
                                branch = entityBranch,
                                deliveryMethod = if (deliveryMethodsEnabled) deliveryMethodId else null,
                                membershipId = sharedPreferences.membershipId.toInt(),
                                deliveryMobile = binding.mobile.text.toString(),
                                deliveryNotes = binding.notes.text.toString()
                            )
                        )
                    }
                    MORSHEDIN_CODE -> {
                        if (deliveryMethod == "Home") {
                            paymentViewModel.getPaymentInfo(
                                PaymentMorshedinBody(
                                    serviceCode = serviceCode,
                                    serviceActionCode = serviceActionCode,
                                    paymentMethod = paymentMethod,
                                    address = binding.address.text.toString(),
                                    membershipId = sharedPreferences.membershipId.toInt(),
                                    deliveryMethod = deliveryMethodId,
                                    deliveryMobile = binding.mobile.text.toString(),
                                    deliveryNotes = binding.notes.text.toString()
                                )
                            )
                        } else {
                            paymentViewModel.getPaymentInfo(
                                PaymentMorshedinBody(
                                    serviceCode = serviceCode,
                                    serviceActionCode = serviceActionCode,
                                    paymentMethod = paymentMethod,
                                    membershipId = sharedPreferences.membershipId.toInt(),
                                    branch = entityBranch,
                                    deliveryMethod = deliveryMethodId,
                                    deliveryMobile = binding.mobile.text.toString(),
                                    deliveryNotes = binding.notes.text.toString()
                                )
                            )
                        }

                    }
                }

            } else {
                verifyPhone()
            }
        }
    }

    private fun verifyPhone() {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(resources.getString(R.string.confirm_phone))
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, resources.getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            val intent = Intent(this, VerifyPhoneActivity::class.java)
            startActivity(intent)
        }
        alertDialog.show()

    }

    private fun showAlertDialog(paymentGatewayReferenceId: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(getString(R.string.payment_reference) + " $paymentGatewayReferenceId")
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.copy_text)
        ) { dialog, _ ->
            dialog.dismiss()
            copyText(paymentGatewayReferenceId)
        }
        alertDialog.show()

    }

    private fun copyText(paymentGatewayReferenceId: String) {
        val clipboard: ClipboardManager =
            getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", paymentGatewayReferenceId)
        clipboard.setPrimaryClip(clip)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PaymentTask.REQUEST_PAYMENT) {
            if (resultCode == PaymentTask.RESULT_PAYMENT) {
                val response =
                    data?.getSerializableExtra(PaymentTask.RESPONSE_DATA) as WebJsResponse?
                handlePaymentResponse(response)
            }
        }
    }

    private fun oPayPayment(paymentEntity: PaymentEntity) {
        referenceCode = paymentEntity.id
        PaymentTask.sandBox = Constants.SANDBOX
        val payInput = PayInput(
            publickey = paymentEntity.publicKey,
            merchantId = paymentEntity.merchantId,
            merchantName = "Neqabty",
            reference = paymentEntity.reference,
            countryCode = "EG", // uppercase
            currency = "EGP", // uppercase
            payAmount = (paymentEntity.totalAmount.toDouble() * 100).toLong(),
            productName = data.title,
            productDescription = data.service,
            callbackUrl = paymentEntity.callBackURL,
            userClientIP = "110.246.160.183",
            expireAt = paymentEntity.expireAt.toInt(),
            paymentType = "BankCard" // optional
        )

        PaymentTask(this).createOrder(payInput, callback = { status, response ->
            when (status) {
                Status.ERROR -> {
                    Toast.makeText(
                        this, response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                }
            }
        })
    }

    var referenceCode = ""
    private fun handlePaymentResponse(response: WebJsResponse?) {
        binding.btnNext.isEnabled = true
        when (response?.orderStatus) {
            PaymentStatus.INITIAL -> {
            }
            PaymentStatus.PENDING -> {

            }
            PaymentStatus.SUCCESS -> {
                val intent = Intent(this, PaymentStatusActivity::class.java)
                intent.putExtra("referenceCode", referenceCode)
                startActivity(intent)
                finish()
            }
            PaymentStatus.FAIL -> {
                showAlert(getString(R.string.payment_canceled)) {}
            }
            PaymentStatus.CLOSE -> {
                showAlert(getString(R.string.payment_canceled)) {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnNext.isEnabled = true
    }

    override fun onError(error: PaymentSdkError) {
        showAlert(getString(R.string.payment_canceled)) {
            finish()
        }
    }

    override fun onPaymentFinish(paymentSdkTransactionDetails: PaymentSdkTransactionDetails) {
        val intent = Intent(this, PaymentStatusActivity::class.java)
        intent.putExtra("referenceCode", referenceCode)
        startActivity(intent)
        finish()
    }

    override fun onPaymentCancel() {
        showAlert(getString(R.string.payment_canceled)) {
            finish()
        }
    }
}