package com.neqabty.healthcare.auth.forgetpassword.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentSendBinding


private const val ARG_PARAM1 = "phone"

class SendFragment : Fragment() {
    private var phone: String? = null
    private lateinit var binding: FragmentSendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phone = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.phone.isEnabled = false
        binding.phone.setText(phone)
        binding.btnSend.setOnClickListener {

            if (binding.phone.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val activity = requireActivity() as ForgetPasswordActivity
            activity.onSendClicked()

        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            SendFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}