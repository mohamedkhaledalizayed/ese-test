package com.neqabty.presentation.ui.mobile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
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
import com.neqabty.databinding.MobileFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.trips.TripsData
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.mobile_fragment.*
import javax.inject.Inject

class MobileFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MobileFragmentBinding>()

    lateinit var mobileViewModel: MobileViewModel
    var type: Int? = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.mobile_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = MobileFragmentArgs.fromBundle(arguments!!)
        type = params.type

        mobileViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MobileViewModel::class.java)

        mobileViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        mobileViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                login()
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
                navController().navigateUp()
            }, message = error?.message)
        })
        initializeViews()
    }

    fun initializeViews() {
        if (PreferencesHelper(requireContext()).mobile.isNotEmpty())
            binding.edMobile.setText(PreferencesHelper(requireContext()).mobile)

        if (!PreferencesHelper(requireContext()).user.equals("null"))
            binding.edMemberNumber.setText(PreferencesHelper(requireContext()).user)

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

    private fun handleViewState(state: MobileViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful) {
            activity?.invalidateOptionsMenu()
            when (type) {
                Constants.CLAIMING -> navController().navigate(
                        MobileFragmentDirections.openClaiming()
                )

                Constants.TRIPS -> navController().navigate(
                        MobileFragmentDirections.openTripReservation(TripsData.tripItem!!)
                )

                Constants.RECORDS -> navController().navigate(
                        MobileFragmentDirections.openEngineeringRecords()
                )

                Constants.UPDATE_DATA -> navController().navigate(
                        MobileFragmentDirections.openUpdateDataVerification()
                )

                Constants.COMPLAINTS -> navController().navigate(
                        MobileFragmentDirections.openComplaints()
                )

                Constants.CORONA -> navController().navigate(
                        MobileFragmentDirections.openCorona()
                )

                Constants.MEDICAL_RENEW -> navController().navigate(
                        MobileFragmentDirections.openMedicalRenew()
                )
            }
        }
    }

    fun login() {
        if (isDataValid(binding.edMobile.text.toString(), binding.edMemberNumber.text.toString())) {
            if (PreferencesHelper(requireContext()).token.isNotBlank())
                mobileViewModel.registerUser(binding.edMobile.text.toString(), binding.edMemberNumber.text.toString(), PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()))
            else {
                FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener(OnCompleteListener { task ->
                            llSuperProgressbar.visibility = View.GONE
                            if (!task.isSuccessful)
                                showAlert("من فضلك تحقق من الإتصال بالإنترنت وحاول مجدداً")
                            else {
                                val token = task.result?.token
                                mobileViewModel.registerUser(binding.edMobile.toString(), binding.edMemberNumber.text.toString(), token!!, PreferencesHelper(requireContext()))
                            }
                        })
            }
        }
    }

    //region
    private fun isDataValid(mobile: String, number: String): Boolean {
        return if (number.isBlank()) {
            showAlert(getString(R.string.invalid_data))
            false
        } else if (number.length > 7 || number.length < 6) {
            showAlert(getString(R.string.invalid_number))
            false
        } else if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(0, 3).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3).equals("011") || mobile.substring(0, 3).equals("015")))
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
// endregion

    fun navController() = findNavController()
}
