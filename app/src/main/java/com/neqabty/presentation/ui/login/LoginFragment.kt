package com.neqabty.presentation.ui.login

import android.os.Bundle
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
import com.neqabty.databinding.LoginFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.HasHomeOptionsMenu
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), Injectable, HasHomeOptionsMenu {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<LoginFragmentBinding>()

    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupToolbar(false)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.login_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle("")
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)

        initializeObservers()
        initializeViews()
    }

    private fun initializeObservers() {
        loginViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        loginViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                login()
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
                navController().navigateUp()
            }, message = error?.message)
        })
    }

    private fun handleViewState(state: LoginViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.user?.let {
            //TODO subscribe
            //TODO set coming data it.details!![0]
            PreferencesHelper(requireContext()).mobile = it.mobile
            PreferencesHelper(requireContext()).userType = it.type
            PreferencesHelper(requireContext()).jwt = it.jwt
            Constants.JWT = it.jwt ?: ""
            PreferencesHelper(requireContext()).mainSyndicate = 5
            PreferencesHelper(requireContext()).subSyndicate = 0
            if(it.type.equals("client")){
                PreferencesHelper(requireContext()).name = it.details!![0].name!!
                PreferencesHelper(requireContext()).user = it.details!![0].userNumber!!
                PreferencesHelper(requireContext()).isRegistered = true
            }
            navController().navigate(LoginFragmentDirections.openHome())
        }
    }

    fun initializeViews() {
        binding.bSend.setOnClickListener {
            ensureLogin()
        }
    }

    fun login() {
        if (isDataValid(edMobile.text.toString())) {
            if (PreferencesHelper(requireContext()).token.isNotBlank())
                loginViewModel.login(edMobile.text.toString(), PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()))
            else {
                FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener(OnCompleteListener { task ->
                            llSuperProgressbar.visibility = View.GONE
                            if (!task.isSuccessful)
                                showAlert("من فضلك تحقق من الإتصال بالإنترنت وحاول مجدداً")
                            else {
                                val token = task.result?.token
                                loginViewModel.login(edMobile.text.toString(), token!!, PreferencesHelper(requireContext()))
                            }
                        })
            }
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

    private fun ensureLogin() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setMessage(getString(R.string.mobile_number_confirmation))
        builder?.setPositiveButton(getString(R.string.alert_confirm)) { dialog, which ->
            login()
            dialog.dismiss()
        }
        builder?.setNegativeButton(getString(R.string.alert_no)) { dialog, which ->
            dialog.dismiss()
        }

        if (dialog == null)
            dialog = builder?.create()

        if (!dialog?.isShowing!!)
            dialog?.show()
    }

    override fun showOptionsMenu() {
    }
// endregion

    fun navController() = findNavController()
}
