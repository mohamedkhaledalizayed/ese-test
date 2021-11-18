package com.neqabty.presentation.ui.changePassword

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.ChangePasswordFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.change_password_fragment.*

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ChangePasswordFragmentBinding>()

    private val changePasswordViewModel: ChangePasswordViewModel by viewModels()

    var isSetNew = false
    var mobile = ""
    var otp = ""
    lateinit var receiver: BroadcastReceiver
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.change_password_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        changePasswordViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        changePasswordViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                changePassword(isSetNew)
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
            }, message = error?.message)
        })
        isSetNew = ChangePasswordFragmentArgs.fromBundle(requireArguments()).isSetNew
        mobile = ChangePasswordFragmentArgs.fromBundle(requireArguments()).mobile
        receiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                if (intent?.action.equals("otp",true)) {
                    val message = intent?.getStringExtra("message") ?: ""
                    otp = message.substring(0, 4)
                    edCurrentPassword.setText(otp)
                }
            }
        }

        initializeViews()
    }

    fun initializeViews() {
        if(isSetNew == true) {
            tvCurrentPassword.setText(getString(R.string.enter_verification_code))
            edCurrentPassword.inputType = InputType.TYPE_CLASS_NUMBER
            edCurrentPassword.setText(otp)
        }

        binding.bSend.setOnClickListener {
            if (binding.edCurrentPassword.text.toString().length < 4 || binding.edNewPassword.text.toString().length < 4 || binding.edConfirmNewPassword.text.toString().length < 4)
                showAlert(getString(R.string.invalid_data))
            else if (!binding.edNewPassword.text.toString().equals(binding.edConfirmNewPassword.text.toString()))
                showAlert(getString(R.string.unmatched_password))
            else
                changePassword(isSetNew)
        }
    }

    private fun handleViewState(state: ChangePasswordViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful) {
            showAlert(state.msg){
                if(isSetNew == true)
                    navController().navigate(R.id.openLoginFragment)
                else
                    navController().navigateUp()
            }
        }
    }

    fun changePassword(isSetNew: Boolean) {
        if(isSetNew == false)
            changePasswordViewModel.changePassword(sharedPref.mobile, binding.edCurrentPassword.text.toString(), edNewPassword.text.toString())
        else
            changePasswordViewModel.setNewPassword(mobile, binding.edCurrentPassword.text.toString(), edNewPassword.text.toString())
    }

    //region
    override fun onResume() {
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter("otp"))
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }
// endregion

    fun navController() = findNavController()
}
