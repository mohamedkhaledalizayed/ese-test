package com.neqabty.presentation.ui.signup

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.R
import com.neqabty.databinding.SignupFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.signup_fragment.*
import javax.inject.Inject

class SignupFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SignupFragmentBinding>()

    lateinit var signupViewModel: SignupViewModel
    var type: Int? = 0

    var newToken = ""
    var otp = ""
    var password = ""
    lateinit var receiver: BroadcastReceiver
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.signup_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = SignupFragmentArgs.fromBundle(arguments!!)
        type = params.type

        signupViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SignupViewModel::class.java)

        signupViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        signupViewModel.errorState.observe(this, Observer { error ->
            showAlert(message = error?.message ?: "")
        })

        receiver = object : BroadcastReceiver() {
            override fun onReceive(contxt: Context?, intent: Intent?) {
                if (intent?.action.equals("otp", true)) {
                    val message = intent?.getStringExtra("message") ?: ""
                    otp = message.substring(0, 4)
                    password = message.substring(4, message.length)
                }
            }
        }
        initializeViews()
    }

    fun initializeViews() {
        newToken = PreferencesHelper(requireContext()).token
        if (PreferencesHelper(requireContext()).mobile.isNotEmpty())
            binding.edMobile.setText(PreferencesHelper(requireContext()).mobile)

        binding.bSend.setOnClickListener {
            login()
        }

        val vto = ivHint.getViewTreeObserver()
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                ivHint.viewTreeObserver.removeOnGlobalLayoutListener(this)
                ivHint.layoutParams.width = ivHint.height
                binding.ivHint.requestLayout()
                binding.edMemberNumber.requestLayout()
            }
        })

        ivHint.setOnClickListener {
            showCardDialog()
        }
    }

    private fun handleViewState(state: SignupViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful && state.user != null) {
            activity?.invalidateOptionsMenu()
            PreferencesHelper(requireContext()).mobile = edMobile.text.toString()
//           PreferencesHelper(requireContext()).user = state.user?.details!![0].userNumber!!
//           PreferencesHelper(requireContext()).name = state.user?.details!![0].name!!
            state.user = null
            navController().navigate(SignupFragmentDirections.openActivateAccountFragment(type!!, otp, password))
        }
    }

    fun login() {
        if (isDataValid(binding.edMobile.text.toString(), binding.edMemberNumber.text.toString(), binding.edNationalNumber.text.toString())) {
            if (newToken.isNotBlank())
                signupViewModel.registerUser(binding.edMemberNumber.text.toString(), binding.edMobile.text.toString(), binding.edNationalNumber.text.toString(),newToken, PreferencesHelper(requireContext()))
            else {
                FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener(OnCompleteListener { task ->
                            llSuperProgressbar.visibility = View.GONE
                            if (!task.isSuccessful)
                                showAlert("من فضلك تحقق من الإتصال بالإنترنت وحاول مجدداً")
                            else {
                                newToken = task.result?.token!!
                                signupViewModel.registerUser(binding.edMemberNumber.text.toString(), binding.edMobile.text.toString(), binding.edNationalNumber.text.toString(), newToken, PreferencesHelper(requireContext()))
                            }
                        })
            }
        }
    }

    //region
    private fun isDataValid(mobile: String, memberNumber: String, nationalNumber: String): Boolean {
        return if (memberNumber.isBlank() || nationalNumber.length != 4) {
            showAlert(getString(R.string.invalid_data))
            false
        } else if (memberNumber.length != 7) {
            showAlert(getString(R.string.invalid_number))
            false
        } else if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(0, 3).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3).equals("011") || mobile.substring(0, 3).equals("015")))
            true
        else {
            showAlert(getString(R.string.invalid_mobile))
            false
        }
    }

    private fun showCardDialog() {
        val dialog = Dialog(requireContext())
        dialog.getWindow()!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(layoutInflater.inflate(R.layout.image_item, null), ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    //    private fun hideKeyboard() {
//        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(activity?.window?.decorView?.rootView?.windowToken, 0)
//    }
//
//    private fun showKeyboard() {
//        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//    }
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
