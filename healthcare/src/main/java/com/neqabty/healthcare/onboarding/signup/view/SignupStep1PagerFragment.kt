package com.neqabty.healthcare.onboarding.signup.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.databinding.FragmentSignupStepOneBinding

class SignupStep1PagerFragment : Fragment() {

    lateinit var binding: FragmentSignupStepOneBinding
    val permissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_SMS
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupStepOneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        grantPermissions()
        initializeViews()
    }

    private fun initializeViews() {
        if(Constants.forTesting) {
            binding.ccp.setCountryForNameCode("EG")
        }
        binding.ccp.registerCarrierNumberEditText(binding.etPhone)
        if((requireActivity() as SignupActivity).sharedPreferences.mobile.isNotBlank()) {
            binding.etPhone.setText((requireActivity() as SignupActivity).sharedPreferences.mobile_without_cc)
//            binding.etPhone.isEnabled = false
        }
    }
    // Handle the permission request result in onRequestPermissionsResult() callback
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            requestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    getSimInfo()
                } else {
                    grantPermissions()
                }
                return
            }
        }
    }

    private fun grantPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getSimInfo()
        } else {
            // Permission is not granted yet, request the permission
            ActivityCompat.requestPermissions(requireActivity(), permissions, 2002)
        }
    }

    private fun getSimInfo() {
        val telephonyManager = requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simCount = telephonyManager.phoneCount

        when(simCount){
            0 -> return
            1 -> {
                binding.clSim1.visibility = View.VISIBLE
                binding.tvNumber1.text = telephonyManager.line1Number
                Toast.makeText(requireActivity(), "1 ," + telephonyManager.line1Number , Toast.LENGTH_LONG).show()
            }
            2 -> {
                binding.clSim1.visibility = View.VISIBLE
                binding.tvNumber1.text = telephonyManager.line1Number

                Toast.makeText(requireActivity(), "1 ," + telephonyManager.line1Number , Toast.LENGTH_LONG).show()
                binding.clSim2.visibility = View.VISIBLE
                binding.tvNumber2.text = telephonyManager.line1Number

                Toast.makeText(requireActivity(), "2 ," + telephonyManager.line1Number , Toast.LENGTH_LONG).show()
            }
        }

    }
}
