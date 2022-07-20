package com.neqabty.healthcare.modules.verifyphone.view.sendotp

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentSendOTPBinding
import com.neqabty.healthcare.modules.verifyphone.view.VerifyPhoneActivity
import com.neqabty.healthcare.modules.verifyphone.view.checkotp.CheckOTPFragment
import com.neqabty.signup.core.utils.isMobileValid


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
            if (binding.phone.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل رقم الهاتف اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!binding.phone.text.toString().isMobileValid()){
                Toast.makeText(requireContext(), "من فضلك ادخل رقم صحيح.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val activity = requireActivity() as VerifyPhoneActivity
            activity.onSendClicked(binding.phone.text.toString())
        }
    }


}