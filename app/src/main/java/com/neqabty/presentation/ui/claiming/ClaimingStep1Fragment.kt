package com.neqabty.presentation.ui.claiming

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.neqabty.R
import com.neqabty.databinding.Claiming1FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.AreaUI
import com.neqabty.presentation.entities.GovernUI
import com.neqabty.presentation.entities.LiteFollowersListUI
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.claiming1_fragment.*

@AndroidEntryPoint
class ClaimingStep1Fragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming1FragmentBinding>()

    private val claimingViewModel: ClaimingViewModel by viewModels()

    var liteFollowersListUI: List<LiteFollowersListUI>? = listOf<LiteFollowersListUI>()
    var governsResultList: List<GovernUI>? = mutableListOf()
    var areasResultList: List<AreaUI>? = mutableListOf()
    var governID: Int = 0
    var areaID: Int = 0
    lateinit var selectedFollower: LiteFollowersListUI
    private var isValid = false
    private var memberName = ""

    lateinit var pager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming1_fragment,
                container,
                false,
                dataBindingComponent
        )
        pager = container as ViewPager
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        claimingViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        claimingViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                claimingViewModel.validateUser(sharedPref.user)
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }, message = error?.message)
        })
        claimingViewModel.validateUser(sharedPref.user)
    }

    private fun handleViewState(state: ClaimingViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
//        if (state.member != null && state.member?.code != 3 && state.member?.code != 4 && !isValid) {
//            val prefs = PreferencesHelper(requireContext())
//            llSuperProgressbar.visibility = View.VISIBLE
//            memberName = state.member!!.engineerName!!
//            isValid = true
//            claimingViewModel.getAllContent1()
//        } else if (state.member?.message != null && !isValid) {
//            showMemberValidationAlert(state.member?.message!!)
//            state.member?.message = null
//        }

        if (state.member != null && !isValid) {
            when (state.member?.code) {
                0 -> {
                    llSuperProgressbar.visibility = View.VISIBLE
                    memberName = state.member!!.engineerName
                    isValid = true
                    claimingViewModel.getAllContent1(sharedPref.mobile, sharedPref.user)
                    state.member = null
                }
                else -> {
                    if (state.member?.message != null) showMemberValidationAlert(state.member?.message ?: getString(R.string.user_not_allowed))
                    state.member?.message = null
                }
            }
        }

        if (state.liteFollowersListUI != null && state.governs != null && state.areas != null && isValid) {
            binding.svContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            state.liteFollowersListUI?.let {
                liteFollowersListUI = it
            }
            state.governs?.let {
                governsResultList = it
            }
            state.areas?.let {
                areasResultList = it
            }
            if (state.liteFollowersListUI != null && state.governs != null && state.areas != null){
//                state.medicalRenewalUI = null
                initializeViews()
            }
        }
    }

    fun initializeViews() {
        if (sharedPref.user.isNotEmpty()) {
            binding.edNumber.setText(sharedPref.user)
            binding.edNumber.isEnabled = false
            binding.edName.setText(memberName)
            binding.edName.isEnabled = false
        }
        binding.bNext.setOnClickListener {
            if (isDataValid(binding.edNumber.text.toString(), binding.edCardNumber.text.toString(), spArea.selectedItem, spGovern.selectedItem)) {
                ClaimingData.areaId = (spArea.selectedItem as AreaUI).id
                ClaimingData.governId = (spGovern.selectedItem as GovernUI).id
                ClaimingData.cardId = edCardNumber.text.toString().toInt()
                ClaimingData.oldbenid = edCardNumber.text.toString()
                ClaimingData.searchProviderName = edServiceProviderName.text.toString()
                ClaimingData.selectedFollower = selectedFollower
                pager.setCurrentItem(1, true)
            }
        }
        initializeSpinners()
    }

    fun initializeSpinners() {
        renderGoverns()
        renderFollowers()
    }

    //region
    fun renderGoverns() {
        binding.spGovern.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, governsResultList!!)
        binding.spGovern.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                governID = (parent.getItemAtPosition(position) as GovernUI).id
                renderAreas()
            }
        }
        binding.spGovern.setSelection(0)
    }

    fun renderAreas() {
        var filteredAreasList: List<AreaUI>? = mutableListOf()

        filteredAreasList = areasResultList?.filter {
            it.govId == governID
        }

        binding.spArea.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, filteredAreasList!!)
        binding.spArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                areaID = (parent.getItemAtPosition(position) as AreaUI).id
            }
        }
    }

    fun renderFollowers() {
        binding.spFollower.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, liteFollowersListUI!!)
        binding.spFollower.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedFollower = parent.getItemAtPosition(position) as LiteFollowersListUI
                edCardNumber.setText(selectedFollower.id.toString())
            }
        }
        binding.spFollower.setSelection(0)
    }

    private fun isDataValid(memberNumber: String, cardNumber: String, area: Any?, govern: Any?): Boolean {
        return if (memberNumber.trim().isNotEmpty() && cardNumber.trim().isNotEmpty() &&
                area != null && govern != null)
            true
        else {
            showInvalidDataAlert()
            false
        }
    }

    private fun showInvalidDataAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.invalid_data))
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showMemberValidationAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.error))
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            dialog.dismiss()
            navController().popBackStack()
            var bundle = Bundle()
            bundle.putInt("type", Constants.CLAIMING)
            navController().navigate(R.id.homeFragment, bundle)
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
// TODO

    // endregion
fun navController() = findNavController()
}
