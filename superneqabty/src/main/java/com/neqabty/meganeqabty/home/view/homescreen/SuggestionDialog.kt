package com.neqabty.meganeqabty.home.view.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.databinding.SuggestionDialogBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SuggestionDialog : DialogFragment() {
    private var phone: String? = ""
    private var email: String? = ""

    private lateinit var binding: SuggestionDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phone = it.getString(ARG_PARAM1)
            email = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SuggestionDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.suggestions.playAnimation()

        if (phone!!.isNotEmpty()){
            binding.mobile.isEnabled = false
            binding.mobile.setText(phone)
            binding.email.setText(email)
        }
        binding.addSuggestion.setOnClickListener {

            when {
                binding.mobile.text.toString().isEmpty() -> {
                    Toast.makeText(context, getString(R.string.enter_phone), Toast.LENGTH_LONG).show()
                }
                binding.email.text.toString().isEmpty() -> {
                    Toast.makeText(context, getString(R.string.enter_email), Toast.LENGTH_LONG).show()
                }
                binding.comment.text.toString().isEmpty() -> {
                    Toast.makeText(context, getString(R.string.enter_message), Toast.LENGTH_LONG).show()
                }
                else -> {
                    val activity = activity as HomeActivity
                    activity.onClick(
                        mobile = binding.mobile.text.toString(),
                        email = binding.email.text.toString(),
                        message = binding.comment.text.toString()
                    )
                    dismiss()
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(mobile: String, email: String) =
            SuggestionDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mobile)
                    putString(ARG_PARAM2, email)
                }
            }
    }
}