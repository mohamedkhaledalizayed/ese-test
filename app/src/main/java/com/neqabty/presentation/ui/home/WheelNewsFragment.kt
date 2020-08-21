package com.neqabty.presentation.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.WheelNewsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.news.NewsAdapter
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.wheel_news_fragment.*
import javax.inject.Inject

class WheelNewsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<WheelNewsFragmentBinding>()

    lateinit var homeViewModel: HomeViewModel
    private var newsAdapter by autoCleared<NewsAdapter>()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.wheel_news_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        homeViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        homeViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                homeViewModel.getNews(PreferencesHelper(requireContext()).mainSyndicate.toString())
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            })
        })
        homeViewModel.getNews(PreferencesHelper(requireContext()).mainSyndicate.toString())
    }

    private fun handleViewState(state: HomeViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        val newsAdapter = NewsAdapter(dataBindingComponent, appExecutors) { newsItem ->
            navController().navigate(
                    HomeFragmentDirections.newsDetails(newsItem)
            )
        }
        this.newsAdapter = newsAdapter
        binding.rvNews.adapter = newsAdapter
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.news?.let {
            if (it.size >= 3) newsAdapter.submitList(it.subList(0, 3)) else newsAdapter.submitList(it)
            bSeemore.visibility = View.VISIBLE
        }

        bSeemore.setOnClickListener { navController().navigate(R.id.newsFragment) }

    }

    //region
    // endregion
    fun navController() = findNavController()
}
