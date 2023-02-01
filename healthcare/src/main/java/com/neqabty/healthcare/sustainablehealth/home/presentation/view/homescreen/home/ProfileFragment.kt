package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.profile.view.changepassword.ChangePasswordDialog
import com.neqabty.healthcare.commen.profile.view.model.PasswordError
import com.neqabty.healthcare.commen.profile.view.profile.ProfileViewModel
import com.neqabty.healthcare.commen.profile.view.update.UpdateInfoActivity
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.FragmentProfileBinding
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {


    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateLicence.visibility = View.GONE
        binding.cardRequest.visibility = View.GONE
        binding.membershipIdLayout.visibility = View.GONE
        binding.view.visibility = View.GONE


        profileViewModel.getUserProfile("Token ${sharedPreferences.token}")
        profileViewModel.user.observe(requireActivity()) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.layoutContainer.visibility = View.VISIBLE

                        binding.name.text = resource.data?.data?.fullName ?: ""
                        binding.mobile.text = resource.data?.data?.mobile
                        binding.phone.text = resource.data?.data?.mobile
                        binding.membershipNumber.text = resource.data?.data?.membershipId.toString()
                        binding.nationalId.text = resource.data?.data?.nationalId.toString()
                        binding.email.text = resource.data?.data?.email ?: ""
                        binding.syndicate.text = resource.data?.data?.entity?.name
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }


        }


        profileViewModel.cardStatus.observe(requireActivity()) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.mobileNumber.text = resources.getString(R.string.phone_number) +
                                " ${resource.data?.mobile}"
                        binding.address.text = resources.getString(R.string.address_) +
                                " ${resource.data?.address}"
                        binding.year.text = resources.getString(R.string.year_) +
                                " ${resource.data?.year}"
                        binding.message.text = resources.getString(R.string.order_status) +
                                " ${resource.data?.statusMessage}"
                        binding.cardBtn.visibility = View.GONE
                        if (resource.data?.statusCode == 3){
                            binding.cardBtn.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.message == "404"){
                            binding.mobileNumber.visibility = View.GONE
                            binding.address.visibility = View.GONE
                            binding.year.visibility = View.GONE
                            binding.cardBtn.visibility = View.VISIBLE
                            binding.message.text = getString(R.string.card_request_not_found)
                        }
                    }
                }
            }


        }


        profileViewModel.licenceStatus.observe(requireActivity()) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        binding.licenceMessage.text = resources.getString(R.string.order_status) +
                                " ${resource.data?.statusMessage}"
                        binding.licenceBtn.visibility = View.GONE
                        if (resource.data?.statusCode == 3){
                            binding.licenceBtn.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.message == "404"){
                            binding.licenceBtn.visibility = View.VISIBLE
                            binding.licenceMessage.text = getString(R.string.licence_not_found)
                        }
                    }
                }
            }


        }


        profileViewModel.password.observe(requireActivity()) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(requireContext(), it.data, Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        val error = Gson().fromJson(resource.message.toString(), PasswordError::class.java)

                        Toast.makeText(requireContext(), error.error, Toast.LENGTH_LONG).show()
                    }
                }
            }


        }

        binding.cardBtn.setOnClickListener {
            val intent = Intent(requireActivity(), UpdateInfoActivity::class.java)
            intent.putExtra("key", 200)
            startActivity(intent)
        }

        binding.licenceBtn.setOnClickListener {
            val intent = Intent(requireActivity(), UpdateInfoActivity::class.java)
            intent.putExtra("key", 100)
            startActivity(intent)
        }

        binding.changePassword.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val dialog = ChangePasswordDialog()
            dialog.show(fm, "")
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
        }

    }

}