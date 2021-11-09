package com.neqabty.presentation.ui.loginWithPassword

import android.os.Bundle
import android.text.Html
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.LoginWithPasswordFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.*
import kotlinx.android.synthetic.main.login_with_password_fragment.*
import javax.inject.Inject

class LoginWithPasswordFragment : BaseFragment(), Injectable, HasHomeOptionsMenu {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<LoginWithPasswordFragmentBinding>()

    lateinit var loginWithPasswordViewModel: LoginWithPasswordViewModel

    var newToken = ""
    var mobile = ""
    var isPasswordVisible = true

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupToolbar(false)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.login_with_password_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle("")
        loginWithPasswordViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginWithPasswordViewModel::class.java)

        mobile = LoginWithPasswordFragmentArgs.fromBundle(requireArguments()).mobile
        initializeObservers()
        initializeViews()
    }

    private fun initializeObservers() {
        loginWithPasswordViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        loginWithPasswordViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.GONE
                edPassword.setText("")
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
                navController().navigateUp()
            }, message = error?.message)
        })
    }

    private fun handleViewState(state: LoginWithPasswordViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.msg.length > 0) {
            showAlert(state.msg) {
                navController().navigateUp()
            }
            state.msg = ""
        }
        state.user?.let {
            //TODO subscribe
            //TODO set coming data it.details!![0]
            sharedPref.isForceLogout = false
            sharedPref.mobile = it.mobile
            sharedPref.userType = it.type
            sharedPref.jwt = it.jwt
            sharedPref.mainSyndicate = 5
            sharedPref.subSyndicate = 0
//                showTwoButtonsAlert(message = getString(R.string.welcome_with_name_login, it.details!![0].name!!)
            sharedPref.name = it.details!![0].name!!
            sharedPref.user = it.details!![0].userNumber!!
            sharedPref.isRegistered = true
            state.user = null
            navController().navigate(LoginWithPasswordFragmentDirections.openHome())
        }
    }

    fun initializeViews() {
        newToken = sharedPref.token
        binding.bSend.setOnClickListener {
            if(isDataValid(edPassword.text.toString()))
                login()
        }
        binding.tvForgetPassword.setOnClickListener {
            showTwoButtonsAlert(Html.fromHtml(getString(R.string.number_confirmation, mobile + "\n"+ getString(R.string.mobile_number_confirmation))).toString(),
                    okCallback = {
                        navController().navigate(LoginWithPasswordFragmentDirections.openForgetPasswordFragment(mobile))
                    }, cancelCallback = {
                navController().navigateUp()
            })
        }
        binding.ivVisibility.setOnClickListener {
            if (isPasswordVisible) {
                isPasswordVisible = false
                binding.edPassword.transformationMethod = null
                binding.ivVisibility.setImageResource(R.drawable.ic_visibile)
            } else {
                isPasswordVisible = true
                binding.edPassword.transformationMethod = PasswordTransformationMethod()
                binding.ivVisibility.setImageResource(R.drawable.ic_invisible)
            }
        }
    }

    fun login() {
        if (newToken.isNotBlank())
            loginWithPasswordViewModel.login(mobile,newToken, sharedPref, edPassword.text.toString())
        else {
            Constants.isFirebaseTokenUpdated.observeOnce(viewLifecycleOwner, Observer {
                if (it.isNotBlank()){
                    newToken = it
                    loginWithPasswordViewModel.login(mobile, newToken!!, sharedPref, edPassword.text.toString())
                }else
                    showAlert(getString(R.string.error_msg))
            })
            PushNotificationsWrapper().getToken(requireContext())
        }
    }
//region

    private fun isDataValid(password: String): Boolean {
        return if (!password.isNullOrBlank())
            true
        else {
            showAlert(getString(R.string.invalid_password))
            false
        }
    }

    private fun showAlert(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(msg)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun showOptionsMenu() {
    }
// endregion

    fun navController() = findNavController()
}
