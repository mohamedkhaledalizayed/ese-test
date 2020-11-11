package com.neqabty.presentation.ui.medicalRenewUpdate

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewUpdateFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_renew_fragment.llContent
import kotlinx.android.synthetic.main.medical_renew_fragment.rvFollowers
import kotlinx.android.synthetic.main.medical_renew_update_fragment.*
import javax.inject.Inject

class MedicalRenewUpdateFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewUpdateFragmentBinding>()
    private var adapter by autoCleared<FollowersUpdateAdapter>()

    @Inject
    lateinit var medicalRenewUpdateViewModel: MedicalRenewUpdateViewModel
    var filteredFollowersList = mutableListOf<MedicalRenewalUI.FollowerItem>()

    lateinit var medicalRenewalUI: MedicalRenewalUI

    lateinit var selectedFollower: MedicalRenewalUI.FollowerItem
    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_renew_update_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        medicalRenewUpdateViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MedicalRenewUpdateViewModel::class.java)

        medicalRenewUpdateViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalRenewUpdateViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalRenewUpdateViewModel.getMedicalRenewalData(PreferencesHelper(requireContext()).user)
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }, message = error?.message)
        })

        medicalRenewUpdateViewModel.getMedicalRenewalData(PreferencesHelper(requireContext()).user)
//        medicalRenewViewModel.getMedicalRenewalData("2305693")
    }

    private fun initializeViews() {
        binding.medicalRenewalData = medicalRenewalUI
        llContent.visibility = View.VISIBLE

        val adapter = FollowersUpdateAdapter(dataBindingComponent, appExecutors,
                removeCallback = { follower ->
                    selectedFollower = follower
                    builder = AlertDialog.Builder(requireContext())
                    builder?.setTitle(getString(R.string.alert_title))
                    builder?.setMessage(getString(R.string.remove_follower_confirmation))
                    builder?.setPositiveButton(getString(R.string.alert_confirm)) { dialog, which ->
                        medicalRenewalUI.followers?.find { it.id == selectedFollower.id }?.isDeleted = true
                        adapter.submitList(medicalRenewalUI.followers?.filter { it.isDeleted == false })
                        adapter.notifyDataSetChanged()
                        rvFollowers.adapter = adapter

                        binding.tvFollowers.visibility = if (medicalRenewalUI.followers?.filter { it.isDeleted == false }?.size == 0) View.INVISIBLE else View.VISIBLE
                    }
                    builder?.setNegativeButton(getString(R.string.alert_no)) { dialog, which ->
                        dialog.dismiss()
                    }

                    if (dialog == null)
                        dialog = builder?.create()

                    if (!dialog?.isShowing!!)
                        dialog?.show()
                    },
                editCallback = { follower ->
                    navController().navigate(MedicalRenewUpdateFragmentDirections.openMedicalRenewFollowerDetailsFragment(follower))
                })
        this.adapter = adapter
        binding.rvFollowers.adapter = adapter
        medicalRenewalUI.followers?.let {
            adapter.submitList(it.filter { it.isDeleted == false }.toMutableList())
        }
        binding.tvFollowers.visibility = if (medicalRenewalUI.followers?.filter { it.isDeleted == false }?.size == 0) View.INVISIBLE else View.VISIBLE

        bAddFollower.setOnClickListener {
            medicalRenewalUI.followers?.get(0).let {
                parentFragmentManager.setFragmentResultListener("bundle",
                        this,
                        FragmentResultListener { key, result ->
                            val result = result.getString("bundle")
                            medicalRenewalUI.followers?.add(medicalRenewalUI.followers?.get(0)!!)
                            binding.tvFollowers.visibility = if (medicalRenewalUI.followers?.filter { it.isDeleted == false }?.size == 0) View.INVISIBLE else View.VISIBLE
                        })
                navController().navigate(MedicalRenewUpdateFragmentDirections.openMedicalRenewFollowerDetailsFragment(it!!))
            }
        }
        bSubmit.setOnClickListener {
        }
    }

    private fun handleViewState(state: MedicalRenewUpdateViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.medicalRenewalUI?.let {
            state.medicalRenewalUI?.contact?.contactID = PreferencesHelper(requireContext()).user
//            filteredFollowersList = state.medicalRenewalUI?.followers?.filter{ it.isDeleted == false }!!.toMutableList()
            medicalRenewalUI = state.medicalRenewalUI!!
            initializeViews()
        }
    }

    //region
// endregion

    fun navController() = findNavController()
}
