package com.neqabty.presentation.ui.about

import androidx.lifecycle.Observer
import android.content.Intent
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.AboutFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.presentation.util.openMap
import com.neqabty.presentation.util.call
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

        aboutViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        aboutViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                aboutViewModel.getSyndicate(sharedPref.mainSyndicate.toString())
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        aboutViewModel.getSyndicate(sharedPref.mainSyndicate.toString())
    }

    private fun handleViewState(state: AboutViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.syndicate?.let {
            svContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
            initializeViews(it)
//            var tempSyndicate = it.copy()
//            tempSyndicate.address = getString(R.string.address_title) + " " + it.address
//            tempSyndicate.phone = getString(R.string.phone_title) + " " + it.phone
//            tempSyndicate.email = getString(R.string.email_title) + " " + it.email
            binding.syndicate = it
        }
    }

    fun initializeViews(syndicate: SyndicateUI) {
        bMap.setOnClickListener {
            syndicate.address?.let { tvAddress.openMap(it, requireContext()) }
        }
        clPhone.setOnClickListener {
            syndicate.phone?.let { tvPhone.call(it, requireContext()) }
        }
        clEmail.setOnClickListener {
            syndicate.email?.let { sendEmail(it) }
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
