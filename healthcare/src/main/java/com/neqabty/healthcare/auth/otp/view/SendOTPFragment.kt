package com.neqabty.healthcare.auth.otp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentSendOTPBinding


private const val ARG_PARAM1 = "param"

class SendOTPFragment : Fragment() {
    private var phoneNumber: String? = null

    private lateinit var binding: FragmentSendOTPBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendOTPBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phoneNumber = it.getString(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.phone.setText(phoneNumber.toString())
        binding.phone.isEnabled = false
        binding.btnSend.setOnClickListener {

            if (binding.phone.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

//            if (!binding.phone.text.toString().isMobileValid()){
//                Toast.makeText(requireContext(), getString(R.string.enter_correct_phone), Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }

            val activity = requireActivity() as VerifyPhoneActivity
            activity.onSendClicked(binding.phone.text.toString())

        }


    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            SendOTPFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}