package com.neqabty.presentation.ui.loginWithPassword

import android.os.Bundle
import android.text.Html
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.R
import com.neqabty.databinding.LoginWithPasswordFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.HasHomeOptionsMenu
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
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
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
                navController().navigateUp()
            }, message = error?.message)
        })
    }

    private fun handleViewState(state: LoginWithPasswordViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if(state.msg.length > 0){
            showAlert(state.msg){
                navController().navigateUp()
            }
            state.msg = ""
        }
        state.user?.let {
            //TODO subscribe
            //TODO set coming data it.details!![0]
            PreferencesHelper(requireContext()).isForceLogout = false
            PreferencesHelper(requireContext()).mobile = it.mobile
            PreferencesHelper(requireContext()).userType = it.type
            PreferencesHelper(requireContext()).jwt = it.jwt
            Constants.JWT = it.jwt ?: ""
            PreferencesHelper(requireContext()).mainSyndicate = 5
            PreferencesHelper(requireContext()).subSyndicate = 0
//                showTwoButtonsAlert(message = getString(R.string.welcome_with_name_login, it.details!![0].name!!)
                PreferencesHelper(requireContext()).name = it.details!![0].name!!
                PreferencesHelper(requireContext()).user = it.details!![0].userNumber!!
                PreferencesHelper(requireContext()).isRegistered = true
                state.user = null
                navController().navigate(LoginWithPasswordFragmentDirections.openHome())
        }
    }

    fun initializeViews() {
        newToken = PreferencesHelper(requireContext()).token
        binding.bSend.setOnClickListener {
            login()
        }
        binding.tvForgetPassword.setOnClickListener{
            showAlert(Html.fromHtml(getString(R.string.number_confirmation, mobile + getString(R.string.mobile_number_confirmation))).toString(),okCallback = {
                loginWithPasswordViewModel.forgetPassword(mobile)
            })
        }
    }

    fun login() {
        if (PreferencesHelper(requireContext()).token.isNotBlank())
            loginWithPasswordViewModel.login(mobile, PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()), edPassword.text.toString())
        else {
            FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener(OnCompleteListener { task ->
                        llSuperProgressbar.visibility = View.GONE
                        if (!task.isSuccessful)
                            showAlert("من فضلك تحقق من الإتصال بالإنترنت وحاول مجدداً")
                        else {
                            newToken = task.result?.token!!
                            loginWithPasswordViewModel.login(mobile, newToken!!, PreferencesHelper(requireContext()), edPassword.text.toString())
                        }
                    })
        }
    }
//region

    private fun isDataValid(mobile: String): Boolean {
        return if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(0, 3).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3).equals("011") || mobile.substring(0, 3).equals("015")))
            true
        else {
            showAlert(getString(R.string.invalid_mobile))
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
