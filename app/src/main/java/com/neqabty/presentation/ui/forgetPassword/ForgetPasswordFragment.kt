package com.neqabty.presentation.ui.forgetPassword

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.ForgetPasswordFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.forget_password_fragment.*

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ForgetPasswordFragmentBinding>()

    private val forgetPasswordViewModel: ForgetPasswordViewModel by viewModels()

    var mobile = ""
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.forget_password_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        forgetPasswordViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        forgetPasswordViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                forgetPassword()
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
            }, message = error?.message)
        })
        mobile = ForgetPasswordFragmentArgs.fromBundle(requireArguments()).mobile
        initializeViews()
    }

    fun initializeViews() {
        binding.edMobile.setText(mobile)
        binding.bSend.setOnClickListener {
            if (isDataValid(edMemberNumber.text.toString(), edNationalNumber.text.toString()))
                forgetPassword()
        }
    }

    private fun handleViewState(state: ForgetPasswordViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful) {
            state.isSuccessful = false
            showAlert(state.msg){
                navController().navigate(ForgetPasswordFragmentDirections.openLoginFragment())
            }
        }
    }

    fun forgetPassword() {
        forgetPasswordViewModel.forgetPassword(mobile, binding.edMemberNumber.text.toString(), binding.edNationalNumber.text.toString())
    }

    //region
    private fun isDataValid(memberNumber: String, nationalNumber: String): Boolean {
        return if (memberNumber.isBlank() || nationalNumber.length != 4) {
            showAlert(getString(R.string.invalid_data))
            false
        } else if (memberNumber.length != 7 && memberNumber.length != 8) {
            showAlert(getString(R.string.invalid_number))
            false
        } else {
            true
        }
    }
// endregion

    fun navController() = findNavController()
}
