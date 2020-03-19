package com.neqabty.presentation.ui.inquiryDetails

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import com.efinance.mobilepaymentsdk.PaymentCreationRequest
import com.efinance.mobilepaymentsdk.PaymentCreationResponse
import com.efinance.mobilepaymentsdk.PaymentGateway
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.InquiryDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.util.CryptoHelp
import com.neqabty.presentation.util.MobilePaymentCreationCallback
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.inquiry_details_fragment.*

import javax.inject.Inject

class InquiryDetailsFragment : BaseFragment(), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

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

        memberItem?.let {
//            var tempMember = it.copy()
//            tempMember.engineerName = getString(R.string.name_title) + " " + it.engineerName
//            tempMember.expirationDate = getString(R.string.expiration_date_title) + " " + it.billDate
//            tempMember.amount = getString(R.string.amount_title) + " " + it.amount + " ج"
            binding.memberItem = it
        }

        bPay.setOnClickListener {
            createPayment()
//            navController().navigate(
//                    InquiryDetailsFragmentDirections.openPayment(memberItem.amount!!.toInt(),memberItem.engineerID!!.toInt())
//            )
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



            if (mechanismTypeButton.getText().toString() == "Card") {
                paymentCreationRequest.PaymentMechanism.Type = PaymentCreationRequest.PaymentMechanismType.Card
            } else if (mechanismTypeButton.getText().toString() == "Channel") {
                paymentCreationRequest.PaymentMechanism.Type = PaymentCreationRequest.PaymentMechanismType.Channel

                paymentCreationRequest.PaymentMechanism.Channel.Email = "xxxx@xx.xx"
                paymentCreationRequest.PaymentMechanism.Channel.MobileNumber = "01111111111"
            } else if (mechanismTypeButton.getText().toString() == "Mobile Wallet") {
                paymentCreationRequest.PaymentMechanism.MobileWallet.MobileNumber = "01111111111"
            } else if (mechanismTypeButton.getText().toString() == "Meeza") {
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

            paymentGateway.CreatePayment(paymentCreationRequest, signature, publicKey, MobilePaymentCreationCallback(activity, mechanismTypeButton.getText().toString()))
        } catch (ex: Exception) {
            Log.i("Error", ex.message)
        }

    }

    // endregion

    fun navController() = findNavController()
}
