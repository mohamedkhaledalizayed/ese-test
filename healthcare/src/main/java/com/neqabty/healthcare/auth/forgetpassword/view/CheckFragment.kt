package com.neqabty.healthcare.auth.forgetpassword.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentCheckBinding


private const val ARG_PARAM1 = "phone"

class CheckFragment : Fragment() {
    private var phone: String? = null

    private lateinit var binding: FragmentCheckBinding
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

        binding = FragmentCheckBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterPhone.text =
            "${getString(R.string.enter_code)} \n $phone"

        binding.btnConfirm.setOnClickListener {
            if (binding.code.text.toString().isNullOrEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.enter_code_), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            val activity = requireActivity() as ForgetPasswordActivity
            activity.onCheckClicked(binding.code.text.toString())
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            CheckFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}