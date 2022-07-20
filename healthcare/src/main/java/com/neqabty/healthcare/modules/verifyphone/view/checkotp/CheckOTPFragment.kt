package com.neqabty.healthcare.modules.verifyphone.view.checkotp

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.neqabty.healthcare.databinding.FragmentCheckOTPBinding
import com.neqabty.healthcare.modules.verifyphone.view.VerifyPhoneActivity


private const val ARG_PARAM1 = "param1"

class CheckOTPFragment : Fragment() {
    private var phoneNumber: String? = null

    private lateinit var binding: FragmentCheckOTPBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            phoneNumber = it.getString(ARG_PARAM1)
        }
    }

    private val updateUIReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (intent != null){
                val code = intent.getStringExtra("code")!!.split(":")[1].trim().toList()
                Log.e("ertyui", code.toString())
                binding.code1.setText(code[0].toString())
                binding.code2.setText(code[1].toString())
                binding.code3.setText(code[2].toString())
                binding.code4.setText(code[3].toString())
                binding.code5.setText(code[4].toString())
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction("service.to.activity")
        requireContext().registerReceiver(updateUIReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(updateUIReceiver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckOTPBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeFocus()

        binding.enterPhone.text = "برجاء إدخال الكود الذى تم إرساله الى رقم الهاتف :  \n  $phoneNumber"
        binding.btnConfirm.setOnClickListener {
            if (binding.code1.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل الكود اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.code2.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل الكود اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.code3.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل الكود اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.code4.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل الكود اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (binding.code5.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك ادخل الكود اولا.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val activity = requireActivity() as VerifyPhoneActivity
            activity.onCheckClicked("${binding.code1.text}${binding.code2.text}${binding.code3.text}${binding.code4.text}${binding.code5.text}")
        }

    }
    private fun changeFocus() {
        binding.code1.requestFocus()
        binding.code1.isCursorVisible = true

        binding.code1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code1.clearFocus()
                    binding.code2.requestFocus()
                    binding.code2.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code1.requestFocus()
                }
            }
        })

        binding.code2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code2.clearFocus()
                    binding.code3.requestFocus()
                    binding.code3.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code2.requestFocus()
                }
            }
        })

        binding.code3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code3.clearFocus()
                    binding.code4.requestFocus()
                    binding.code4.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code3.requestFocus()
                }
            }
        })

        binding.code4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code4.clearFocus()
                    binding.code5.requestFocus()
                    binding.code5.isCursorVisible = true
                } else if (editable.isEmpty()) {
                    binding.code4.requestFocus()
                }
            }
        })

        binding.code5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length == 1) {
                    binding.code5.requestFocus()
                    binding.code5.isCursorVisible = false
                    KeyboardUtils.hideKeyboard(requireActivity())

                } else if (editable.isEmpty()) {
                    binding.code5.requestFocus()
                }
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            CheckOTPFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}

class KeyboardUtils{

    companion object{
        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view: View? = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}