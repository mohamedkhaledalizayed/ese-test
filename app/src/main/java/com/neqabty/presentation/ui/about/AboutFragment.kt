package com.neqabty.presentation.ui.about

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.AboutFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared

import kotlinx.android.synthetic.main.about_fragment.*
import javax.inject.Inject

class AboutFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<AboutFragmentBinding>()

    lateinit var aboutViewModel: AboutViewModel

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
        aboutViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AboutViewModel::class.java)

        aboutViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        aboutViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                aboutViewModel.getSyndicate(PreferencesHelper(requireContext()).mainSyndicate.toString())
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        aboutViewModel.getSyndicate(PreferencesHelper(requireContext()).mainSyndicate.toString())
    }

    private fun handleViewState(state: AboutViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.syndicate?.let {
            initializeViews(it)
            var tempSyndicate = it.copy()
            tempSyndicate.address = getString(R.string.address_title) + " " + it.address
            tempSyndicate.phone = getString(R.string.phone_title) + " " + it.phone
            tempSyndicate.email = getString(R.string.email_title) + " " + it.email
            binding.syndicate = tempSyndicate
        }
    }

    fun initializeViews(syndicate: SyndicateUI) {
        tvAddress.setOnClickListener {
            syndicate?.address?.let { openMaps(it) }
        }
        tvPhone.setOnClickListener {
            syndicate?.phone?.let { call(it) }
        }
        tvEmail.setOnClickListener {
            syndicate?.email?.let { sendEmail(it) }
        }
    }

    //region
    private fun openMaps(address: String) {
        val uri = "geo:0,0?q=$address"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        requireContext().startActivity(intent)
    }

    private fun call(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }

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
