package com.neqabty.presentation.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.ui.news.NewsFragmentDirections
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
            (binding.rvBranches.adapter as BranchesAdapter).submitList(state.branches)
            initializeViews(state)
        }
    }

    fun initializeViews(state: AboutViewState) {
        bMap.setOnClickListener {
            state.syndicate?.address?.let { tvAddress.openMap(it, requireContext()) }
        }
        clPhone.setOnClickListener {
            state.syndicate?.phone?.let { tvPhone.call(it, requireContext()) }
        }
        clEmail.setOnClickListener {
            state.syndicate?.email?.let { sendEmail(it) }
        }
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
// endregion

    fun navController() = findNavController()
}
