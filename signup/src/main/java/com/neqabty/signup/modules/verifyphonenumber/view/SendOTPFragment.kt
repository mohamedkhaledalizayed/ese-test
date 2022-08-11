package com.neqabty.signup.modules.verifyphonenumber.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.neqabty.core.utils.isMobileValid
import com.neqabty.signup.databinding.FragmentSendOTPBinding


class SendOTPFragment : Fragment() {

    private lateinit var binding: FragmentSendOTPBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendOTPBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSend.setOnClickListener {

            if (binding.phone.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل رقم الهاتف.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!binding.phone.text.toString().isMobileValid()){
                Toast.makeText(requireContext(), "من فضلك ادخل رقم الهاتف صحيح.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val activity = requireActivity() as VerifyPhoneActivity
            activity.onSendClicked(binding.phone.text.toString())

        }


    }

}