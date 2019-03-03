package com.neqabty.presentation.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.HomeFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.news.NewsAdapter
import com.neqabty.presentation.util.HasOptionsMenu
import com.neqabty.presentation.util.OnBackPressedListener
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

@OpenForTesting
class HomeFragment : BaseFragment(), Injectable,OnBackPressedListener, HasOptionsMenu {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<HomeFragmentBinding>()
    private var adapter by autoCleared<NewsAdapter>()

    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
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


        val adapter = NewsAdapter(dataBindingComponent, appExecutors) { newsItem ->
            navController().navigate(
                    HomeFragmentDirections.newsDetails(newsItem)
            )
        }
        this.adapter = adapter
        binding.rvNews.adapter = adapter

        homeViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        homeViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(),retryCallback =  {
                binding.progressbar.visibility = View.VISIBLE
                homeViewModel.getNews(PreferencesHelper(requireContext()).mainSyndicate.toString())
            }, cancelCallback = {
                navController().navigateUp()
            })
        })

        homeViewModel.getNews(PreferencesHelper(requireContext()).mainSyndicate.toString())

    }

    override fun onResume() {
        super.onResume()
        initializeViews()
    }

    private fun handleViewState(state: HomeViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.news?.let {
            adapter.submitList(it)
        }
    }

    fun initializeViews() {
        (activity as AppCompatActivity).drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        binding.llClaiming.setOnClickListener{
            if (PreferencesHelper(requireContext()).isRegistered)
                navController().navigate(R.id.claimingFragment)
            else
                navController().navigate(R.id.mobileFragment)
        }
        binding.llNews.setOnClickListener{
            navController().navigate(R.id.newsFragment)
        }
        binding.llTrips.setOnClickListener{
            navController().navigate(R.id.tripsFragment)
        }
        binding.llMedical.setOnClickListener{
            navController().navigate(R.id.medicalCategoriesFragment)
        }
    }


    override fun onBackPressed() {
    }

    override fun showOptionsMenu() {
    }
//region


// endregion

    fun navController() = findNavController()
}
