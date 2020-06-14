package com.neqabty.presentation.ui.complaint

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.ComplaintFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.ComplaintTypeUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.complaint_fragment.*
import javax.inject.Inject

class ComplaintFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<ComplaintFragmentBinding>()

    @Inject
    lateinit var complaintViewModel: ComplaintViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var complaintsTypesList: List<ComplaintTypeUI>? = mutableListOf()
    var complaintsTypeID: Int = 0
    var isSubmitted: Boolean = false
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.complaint_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        complaintViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ComplaintViewModel::class.java)

        complaintViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        complaintViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                complaintViewModel.getTypes()
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            })
        })
        complaintViewModel.getTypes()
        initializeViews()
    }

    private fun initializeViews() {
        binding.edMobile.setText(PreferencesHelper(requireContext()).mobile)
        binding.bNext.setOnClickListener {
            isSubmitted = true
            complaintViewModel.createComplaint(edName.text.toString(), edMobile.text.toString(), complaintsTypeID.toString(), edBody.text.toString(), PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()).user)
        }
    }

    private fun handleViewState(state: ComplaintViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading && state.message.isNotBlank() && isSubmitted) {
            isSubmitted = false
            showSuccessAlert()
        } else if (!state.isLoading && state.types != null) {
            binding.llHolder.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            state.types?.let {
                complaintsTypesList = it
            }
            renderTypes()
        }
    }

    //region
    fun renderTypes() {
        binding.spTypes.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, complaintsTypesList)
        binding.spTypes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                complaintsTypeID = (parent.getItemAtPosition(position) as ComplaintTypeUI).id
            }
        }
        binding.spTypes.setSelection(0)
    }


    fun showSuccessAlert() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(getString(R.string.complaint_sent))
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().navigateUp()
        }
        var dialog = builder?.create()
        dialog?.show()
    }


// endregion

    fun navController() = findNavController()
}