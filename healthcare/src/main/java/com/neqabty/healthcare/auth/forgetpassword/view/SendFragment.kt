package com.neqabty.healthcare.auth.forgetpassword.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentSendBinding



class SendFragment : Fragment() {

    private lateinit var binding: FragmentSendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ccp.registerCarrierNumberEditText(binding.phone)
        binding.btnSend.setOnClickListener {

            if (binding.phone.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.ccp.isValidFullNumber) {
                val activity = requireActivity() as ForgetPasswordActivity
                activity.onSendClicked(binding.ccp.fullNumberWithPlus)
            }else{
                Toast.makeText(requireContext(), "الرقم الذى ادخلته غير صحيح.", Toast.LENGTH_LONG).show()
            }

        }

    }

}