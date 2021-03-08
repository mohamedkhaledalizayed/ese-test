package com.neqabty.presentation.ui.forgetPassword

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.ForgetPasswordFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.forget_password_fragment.*
import javax.inject.Inject

class ForgetPasswordFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ForgetPasswordFragmentBinding>()

    lateinit var forgetPasswordViewModel: ForgetPasswordViewModel

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
                R.layout.forget_password_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        forgetPasswordViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ForgetPasswordViewModel::class.java)

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
        receiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                if (intent?.action.equals("otp",true)) {
                    val message = intent?.getStringExtra("message") ?: ""
                    otp = message.substring(0, 4)
                }
            }
        }

        initializeViews()
    }

    fun initializeViews() {
        binding.edMobile.setText(mobile)
        binding.bSend.setOnClickListener {
            if (isDataValid(edMemberNumber.text.toString()))
                forgetPassword()
        }
    }

    private fun handleViewState(state: ForgetPasswordViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful) {
            state.isSuccessful = false
            showAlert(state.msg){
                navController().navigate(ForgetPasswordFragmentDirections.openChangePasswordFragment(true, mobile, otp))
            }
        }
    }

    fun forgetPassword() {
        forgetPasswordViewModel.forgetPassword(mobile, binding.edMemberNumber.text.toString())
    }

    //region
    private fun isDataValid(memberNumber: String): Boolean {
        return if (memberNumber.isBlank()) {
            showAlert(getString(R.string.invalid_data))
            false
        } else if (memberNumber.length != 7) {
            showAlert(getString(R.string.invalid_number))
            false
        } else {
            true
        }
    }
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
