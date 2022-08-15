package com.neqabty.presentation.ui.signup

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.Window
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.messaging.FirebaseMessaging
import com.neqabty.presentation.util.PushNotificationsWrapper
import com.neqabty.presentation.common.Constants
import com.neqabty.R
import com.neqabty.databinding.SignupFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.ui.trips.TripsData
import com.neqabty.presentation.util.autoCleared
import com.neqabty.presentation.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.signup_fragment.*

@AndroidEntryPoint
class SignupFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SignupFragmentBinding>()

    private val signupViewModel: SignupViewModel by viewModels()

    var type: Int? = 0

    var newToken = ""
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

        signupViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        signupViewModel.errorState.observe(this, Observer { error ->
            showAlert(message = error?.message ?: "")
        })
        initializeViews()
    }

    fun initializeViews() {
        newToken = sharedPref.token
        if (sharedPref.mobile.isNotEmpty())
            binding.edMobile.setText(sharedPref.mobile)

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
            sharedPref.mobile = edMobile.text.toString()
            sharedPref.jwt = state?.user?.jwt
            sharedPref.user = state.user?.details!![0].userNumber!!
            sharedPref.name = state.user?.details!![0].name!!
            sharedPref.userSection = state.user?.section!!
            sharedPref.userSyndicate = state.user?.syndicate!!
            sharedPref.userSectionID = state.user?.sectionID!!
            sharedPref.userSyndicateID = state.user?.syndicateID!!
            sharedPref.isRegistered = true
            FirebaseMessaging.getInstance().subscribeToTopic(state.user?.section!!)
            FirebaseMessaging.getInstance().subscribeToTopic(state.user?.syndicate!!)
            activity?.invalidateOptionsMenu()
            state.user = null
            showTwoButtonsAlert(message = getString(R.string.welcome_with_name, sharedPref.name)+"\n"+getString(R.string.welcome_with_password),
                    okCallback = {
                        navController().navigate(SignupFragmentDirections.openChangePasswordFragment(false, sharedPref.mobile))
                    }, cancelCallback = {
                continueNavigation()
            }, btnPositiveTitle = getString(R.string.change_password_title), btnNegativeTitle = getString(R.string.continue_btn))
        }
    }

    fun login() {
        if (isDataValid(binding.edMobile.text.toString(), binding.edMemberNumber.text.toString(), binding.edNationalNumber.text.toString())) {
            if (newToken.isNotBlank())
                signupViewModel.registerUser(binding.edMemberNumber.text.toString(), binding.edMobile.text.toString(), binding.edNationalNumber.text.toString(), newToken, sharedPref)
            else {
                Constants.isFirebaseTokenUpdated.observeOnce(viewLifecycleOwner, Observer {
                    if (it.isNotBlank()) {
                        newToken = it
                        signupViewModel.registerUser(binding.edMemberNumber.text.toString(), binding.edMobile.text.toString(), binding.edNationalNumber.text.toString(), newToken, sharedPref)
                    } else
                        showAlert(getString(R.string.error_msg))
                })
                PushNotificationsWrapper().getToken(requireContext())
            }
        }
    }

    //region
    private fun isDataValid(mobile: String, memberNumber: String, nationalNumber: String): Boolean {
        return if (memberNumber.isBlank() || nationalNumber.length != 4) {
            showAlert(getString(R.string.invalid_data))
            false
        } else if (memberNumber.length != 7 && memberNumber.length != 8) {
            showAlert(getString(R.string.invalid_number))
            false
        } else
            true
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

    private fun continueNavigation() {
        when (type) {
            Constants.CLAIMING -> navController().navigate(
                    SignupFragmentDirections.openClaiming()
            )

            Constants.TRIPS -> navController().navigate(
                    SignupFragmentDirections.openTripReservation(TripsData.tripItem!!)
            )

            Constants.RECORDS -> navController().navigate(
                    SignupFragmentDirections.openEngineeringRecords()
            )

            Constants.UPDATE_DATA -> navController().navigate(
                    SignupFragmentDirections.openUpdateDataVerification()
            )

            Constants.COMPLAINTS -> navController().navigate(
                    SignupFragmentDirections.openComplaints()
            )

            Constants.QUESTIONNAIRE -> navController().navigate(
                    SignupFragmentDirections.openQuestionnaire()
            )

            Constants.MEDICAL_RENEW -> navController().navigate(
                    SignupFragmentDirections.openMedicalRenew()
            )

            Constants.ONLINE_PHARMACY -> navController().navigate(
                    SignupFragmentDirections.openOnlinePharmacy()
            )

            Constants.DOCTORS_RESERVATION -> navController().navigate(
                    SignupFragmentDirections.openDoctorsReservation()
            )

            Constants.MEDICAL_LETTERS -> navController().navigate(
                    SignupFragmentDirections.openMedicalLetters()
            )

            Constants.MEDICAL_LETTERS_INQUIRY -> navController().navigate(
                    SignupFragmentDirections.openMedicalLettersInquiry()
            )

            Constants.TRACK_SHIPMENT -> navController().navigate(
                    SignupFragmentDirections.openTrackShipment()
            )

            Constants.CHANGE_USER_MOBILE -> navController().navigate(
                    SignupFragmentDirections.openChangeUserMobile()
            )

            Constants.CHANGE_PASSWORD -> navController().navigate(
                    SignupFragmentDirections.openChangePassword(false, sharedPref.mobile)
            )

            Constants.COMMITTEES -> navController().navigate(
                SignupFragmentDirections.openCommittees()
            )

            Constants.MEDICAL_PROCEDURES_INQUIRY -> navController().navigate(
                SignupFragmentDirections.openMedicalProceduresInquiry()
            )

            Constants.REFUND -> navController().navigate(
                SignupFragmentDirections.openRefund()
            )
        }
    }
// endregion

    fun navController() = findNavController()
}
