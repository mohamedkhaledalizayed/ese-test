package com.neqabty.presentation.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.LoginFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class LoginFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<LoginFragmentBinding>()

    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        setupToolbar(false)
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
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)

//        initializeObservers()
        initializeViews()
        loginViewModel.login("01119850766","123@pass" , "c4baf341d52e53c01dd0ff4e2f930ab24886f22c5ef1b15e715534832c0e9528")

//        loginViewModel.signup("m@m.m", "Mona", "Mohamed", "01119850766", "1", "1", "1", "123@pass")

    }

    private fun initializeGPS() {
        loginViewModel.locationManager = this.context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager;
    }

    private fun initializeObservers() {
        loginViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        loginViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
            }
        })

        if (loginViewModel.photoFilePath.isNotBlank() && loginViewModel.weatherText.isBlank())
            initializeGPS()

    }

    private fun handleViewState(state: LoginViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.user?.let {
        }
    }

    fun initializeViews() {
        binding.tvSignup.setOnClickListener{
            navController().navigate(LoginFragmentDirections.signup())
        }
//        binding.bCapture.setOnClickListener {
//            grantCameraPermission()
//        }
//
//        binding.bHistory.setOnClickListener {
//            //        navController().navigate(
////                CaptureFragmentDirections.showHistory()
////        )
//        }
//
//        binding.bShare.setOnClickListener { ImageUtils.share(this.context!!, loginViewModel.mCurrentPhotoPath) }
//        binding.weatherLoaded = false
//        binding.photoURL = ""
    }


//region

    private fun displayMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

// endregion

    fun navController() = findNavController()
}
