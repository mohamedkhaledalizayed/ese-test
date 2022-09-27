package com.neqabty.presentation.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.AboutFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.SyndicateBranchUI
import com.neqabty.presentation.util.autoCleared
import com.neqabty.presentation.util.call
import com.neqabty.presentation.util.openMap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.about_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<AboutFragmentBinding>()

    private val aboutViewModel: AboutViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.about_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.rvBranches.adapter = BranchesAdapter(dataBindingComponent, appExecutors) {}

        aboutViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        aboutViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                aboutViewModel.getSyndicate(sharedPref.mainSyndicate.toString())
                aboutViewModel.getSyndicateBranches()
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        aboutViewModel.getSyndicate(sharedPref.mainSyndicate.toString())
        aboutViewModel.getSyndicateBranches()
    }

    private fun handleViewState(state: AboutViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if(state.syndicate != null && state.branches != null){
            svContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            binding.syndicate = state.syndicate
            renderGoverns(state.branches!!)
            initializeViews(state)
        }
    }

    fun initializeViews(state: AboutViewState) {
        binding.layoutMain.tvTitle.text = getString(R.string.mainsyndicate_title)
        binding.layoutMain.tvAddress.text = state.syndicate?.address
        binding.layoutMain.tvAddress.setOnClickListener {
            state.syndicate?.address?.let { binding.layoutMain.tvAddress.openMap(it, requireContext()) }
        }

        binding.layoutMain.tvPhone.text = state.syndicate?.phone
        binding.layoutMain.tvPhone.setOnClickListener {
            state.syndicate?.phone?.let { binding.layoutMain.tvPhone.call(it, requireContext()) }
        }

        binding.layoutMain.tvEmail.text = state.syndicate?.email
        binding.layoutMain.tvEmail.setOnClickListener {
            state.syndicate?.email?.let { sendEmail(it) }
        }

        binding.tvSubsyndicates.visibility = if (Constants.isSyndicatesListEnabled.value == true) View.VISIBLE else View.GONE
        binding.spGoverns.visibility = if (Constants.isSyndicatesListEnabled.value == true) View.VISIBLE else View.GONE
        binding.rvBranches.visibility = if (Constants.isSyndicatesListEnabled.value == true) View.VISIBLE else View.GONE

        Constants.isSyndicatesListEnabled.observe(this, Observer {
            binding.tvSubsyndicates.visibility = if (Constants.isSyndicatesListEnabled.value == true) View.VISIBLE else View.GONE
            binding.spGoverns.visibility = if (Constants.isSyndicatesListEnabled.value == true) View.VISIBLE else View.GONE
            binding.rvBranches.visibility = if (Constants.isSyndicatesListEnabled.value == true) View.VISIBLE else View.GONE
        })
    }

    //region

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        val uriText = "mailto:" + Uri.encode(email)
        val uri = Uri.parse(uriText)
        intent.setData(uri)
        if (intent.resolveActivity(requireContext().packageManager) != null)
            startActivity(intent)
    }

    fun renderGoverns(branches: List<SyndicateBranchUI>) {
        binding.spGoverns.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, branches)
        binding.spGoverns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val branchesList = branches.filter { it.id == (parent.getItemAtPosition(position) as SyndicateBranchUI).id }
                (binding.rvBranches.adapter as BranchesAdapter).submitList(branchesList)
                (binding.rvBranches.adapter as BranchesAdapter).notifyDataSetChanged()
            }
        }
        binding.spGoverns.setSelection(0)
    }
    
// endregion

    fun navController() = findNavController()
}
