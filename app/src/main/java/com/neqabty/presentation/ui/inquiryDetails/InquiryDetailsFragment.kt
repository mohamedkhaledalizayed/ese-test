package com.neqabty.presentation.ui.inquiryDetails

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.efinance.mobilepaymentsdk.*
import com.neqabty.AppExecutors
import com.neqabty.MainActivity
import com.neqabty.R
import com.neqabty.databinding.InquiryDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.util.CryptoHelp
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.inquiry_details_fragment.*

import javax.inject.Inject

class InquiryDetailsFragment : BaseFragment(), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var adapter by autoCleared<PaymentItemsAdapter>()
    var binding by autoCleared<InquiryDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var memberItem: MemberUI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.inquiry_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews()
    }

    fun initializeViews() {
        val params = InquiryDetailsFragmentArgs.fromBundle(arguments!!)
        memberItem = params.memberItem

        val adapter = PaymentItemsAdapter(dataBindingComponent, appExecutors) { }
        this.adapter = adapter


        memberItem?.let {
            binding.memberItem = it

            it.payments?.let {
                adapter.submitList(it)
            }
        }


        binding.rvDetails.adapter = adapter
        bPay.setOnClickListener {
//            createPayment()
            navController().navigate(
                    InquiryDetailsFragmentDirections.openPayment()
            )
        }
    }


    //region

    fun createPayment() {
        try {

            val paymentGateway = PaymentGateway(activity, "1234")
            val paymentCreationRequest = PaymentCreationRequest()

            paymentCreationRequest.Sender.Id = "028"
            paymentCreationRequest.Sender.Name = "MSAD"
            paymentCreationRequest.Sender.Password = "1234" //todo
            paymentCreationRequest.Description = "Test"
            paymentCreationRequest.SenderInvoiceNumber = "123456789"
            paymentCreationRequest.AdditionalInfo = "Test"

            paymentCreationRequest.SenderRequestNumber = "443064294"

            paymentCreationRequest.ServiceCode = "132"

            val settlementAmount = PaymentCreationRequest.SettlementAmount()

            settlementAmount.Amount = java.lang.Double.valueOf("25")
            settlementAmount.SettlementAccountCode = 255
            settlementAmount.Description = "Test"

            paymentCreationRequest.SettlementAmounts.add(settlementAmount)

            paymentCreationRequest.Currency = "818"

            val mechanismTypeButton: RadioButton = binding.root.findViewById(rgPaymentMechanismType.getCheckedRadioButtonId())



            if (mechanismTypeButton.getText().toString() == getString(R.string.payment_card)) {
                paymentCreationRequest.PaymentMechanism.Type = PaymentCreationRequest.PaymentMechanismType.Card
            } else if (mechanismTypeButton.getText().toString() == getString(R.string.payment_channel)) {
                paymentCreationRequest.PaymentMechanism.Type = PaymentCreationRequest.PaymentMechanismType.Channel

                paymentCreationRequest.PaymentMechanism.Channel.Email = "xxxx@xx.xx"
                paymentCreationRequest.PaymentMechanism.Channel.MobileNumber = "01111111111"
            } else if (mechanismTypeButton.getText().toString() == getString(R.string.payment_wallet)) {
                paymentCreationRequest.PaymentMechanism.MobileWallet.MobileNumber = "01111111111"
            } else if (mechanismTypeButton.getText().toString() == getString(R.string.payment_meeza)) {
                paymentCreationRequest.PaymentMechanism.Meeza.Tahweel.MobileNumber = "01111111111"
            }

            paymentCreationRequest.RequestExpiryDate = "2020-04-01"

            paymentCreationRequest.UserUniqueIdentifier = "12346743298546"

            val signature = CryptoHelp.signData(paymentCreationRequest.serialize(), context)

            val publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0MAVRsQax/cxZwZcfp+s\n" +
                    "0mzZtFGNRpuRLnyKUosdnlHn2JC1FSHpnmsON1Mp/SY+GR/Wk1kO2QGNNdszikPg\n" +
                    "AnN5/fcX6JPIkzSHCMLqPxUNqaCfn0eaLrwgsc1SCwlm+f8c+CseG3OeR+sUdq52\n" +
                    "PHf7edpjC60V4bNo/gEVRLV+VsvBde8jhet6Z/wRrKL5K1MQH0ByYn9upf96myRi\n" +
                    "TOSvocuBVHnlb2O+tapLVrNq7dMNXCHHB7IuNCFvP0f0QILeDW5CxebcsTItzxLL\n" +
                    "urKtA1lTWv+Ao9oqexy1BJzMytpS+BAz9kNzmt/g7RcdbXd0MxFotvoHjl2jwE1w\n" +
                    "LwIDAQAB"

            paymentGateway.CreatePayment(paymentCreationRequest, signature, publicKey, MobilePaymentCreationCallback(requireContext(), mechanismTypeButton.getText().toString()))
        } catch (ex: Exception) {
            Log.i("Error", ex.message)
        }

    }

    // endregion

// region callback
    class MobilePaymentCreationCallback(private val context: Context, private val paymentMethod: String) : PaymentCreationCallback {

        override fun onSuccess(response: PaymentCreationResponse) {
            Log.i("NEQABTY", "Request Completed Successfully")

            Toast.makeText(context, "Payment Created Successfully", Toast.LENGTH_LONG).show()

            if (paymentMethod == context.getString(R.string.payment_card)) {
                //            Intent intent = new Intent(CreatePaymentActivity.this, ConfirmPaymentActivity.class);
                val intent = Intent(context, MainActivity::class.java)

                intent.putExtra("senderRequestNumber", response.OriginalSenderRequestNumber)
                intent.putExtra("cardRequestNumber", response.CardRequestNumber)
                intent.putExtra("sessionID", response.SessionId)
                intent.putExtra("amount", java.lang.Double.toString(response.TotalAuthorizationAmount))

                context.startActivity(intent)
            } else if (paymentMethod == context.getString(R.string.payment_channel) ||
                    paymentMethod == context.getString(R.string.payment_wallet) ||
                    paymentMethod == context.getString(R.string.payment_meeza)) {

                Toast.makeText(context, "Use this number as a reference: " + response.CardRequestNumber, Toast.LENGTH_LONG).show()
                //            Intent intent = new Intent(CreatePaymentActivity.this, PaymentStatusInquiryActivity.class);

                val intent = Intent(context, MainActivity::class.java)

                intent.putExtra("senderRequestNumber", response.OriginalSenderRequestNumber)

                context.startActivity(intent)
            }

        }

        override fun onError(paymentException: PaymentException) {
            Log.e("NEQABTY", paymentException.details.message)
        }
    }
    //endregion

    fun navController() = findNavController()
}
