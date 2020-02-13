package com.neqabty.presentation.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.BuildConfig
import com.neqabty.R
import com.neqabty.databinding.HomeFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.news.NewsAdapter
import com.neqabty.presentation.util.HasHomeOptionsMenu
import com.neqabty.presentation.util.OnBackPressedListener
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared

import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), Injectable, OnBackPressedListener, HasHomeOptionsMenu {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<HomeFragmentBinding>()
    private var newsAdapter by autoCleared<NewsAdapter>()
    private var tripsAdapter by autoCleared<TripsAdapter>()

    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var isAlertShown = false
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.home_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.mipmap.menu_ic)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        val newsAdapter = NewsAdapter(dataBindingComponent, appExecutors) { newsItem ->
            navController().navigate(
                    HomeFragmentDirections.newsDetails(newsItem)
            )
        }
        this.newsAdapter = newsAdapter
        binding.rvNews.adapter = newsAdapter

        val tripsAdapter = TripsAdapter(dataBindingComponent, appExecutors) { tripsItem ->
            navController().navigate(
                    HomeFragmentDirections.tripDetails(tripsItem)
            )
        }
        this.tripsAdapter = tripsAdapter
        binding.rvTrips.adapter = tripsAdapter

        homeViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        homeViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                homeViewModel.getContent(PreferencesHelper(requireContext()).mainSyndicate.toString(), PreferencesHelper(context!!).user)
            }, cancelCallback = {
                navController().navigateUp()
            })
        })

        homeViewModel.getContent(PreferencesHelper(requireContext()).mainSyndicate.toString(), PreferencesHelper(context!!).user)
    }

    override fun onResume() {
        super.onResume()
        initializeViews()
    }

    private fun handleViewState(state: HomeViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.news?.let {
            binding.tvNews.visibility = View.VISIBLE
            newsAdapter.submitList(it)
        }
        state.trips?.let {
            binding.tvTrips.visibility = View.VISIBLE
            tripsAdapter.submitList(it.subList(0, 5))
        }
        state.appVersion?.let {
            if (BuildConfig.VERSION_CODE < it) {
                if (!isAlertShown)
                    showAlert()
            }
        }
        state.notificationsCount?.let {
            PreferencesHelper(requireContext()).notificationsCount = it
            activity?.invalidateOptionsMenu()
        }
    }

    fun initializeViews() {
        (activity as AppCompatActivity).drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        binding.llClaiming.setOnClickListener {
            if (PreferencesHelper(requireContext()).isRegistered)
                navController().navigate(R.id.claimingFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", 1)
                navController().navigate(R.id.mobileFragment, bundle)
            }
        }
        binding.llNews.setOnClickListener {
            navController().navigate(R.id.newsFragment)
        }
        binding.llTrips.setOnClickListener {
            navController().navigate(R.id.tripsFragment)
        }
        binding.llMedical.setOnClickListener {
            navController().navigate(R.id.chooseAreaFragment)
        }
//        binding.llInquiry.setOnClickListener {
//            navController().navigate(R.id.inquiryFragment)
//        }
    }

    override fun onBackPressed() {
    }

    override fun showOptionsMenu() {
    }

    //region
    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.update_msg))
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            val appPackageName = requireContext().packageName
            try {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")
                        )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                )
            }
            showAlert()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        isAlertShown = true
    }
// endregion

    fun navController() = findNavController()
}
