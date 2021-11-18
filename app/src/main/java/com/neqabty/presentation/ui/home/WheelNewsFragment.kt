package com.neqabty.presentation.ui.home

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.WheelNewsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.ui.news.NewsAdapter
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.wheel_news_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class WheelNewsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<WheelNewsFragmentBinding>()

    private val homeViewModel: HomeViewModel by viewModels()
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

        homeViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        homeViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                homeViewModel.getNews(sharedPref.mainSyndicate.toString())
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }, message = error?.message)
        })
        homeViewModel.getNews(sharedPref.mainSyndicate.toString())

        binding.clQuestionnaire.visibility = if(Constants.hasQuestionnaire.value == true) View.VISIBLE else View.GONE
        Constants.hasQuestionnaire.observe(this, Observer {
            binding.clQuestionnaire.visibility = if(it == true) View.VISIBLE else View.GONE
        })
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
            if (it.size >= 5) newsAdapter.submitList(it.subList(0, 5)) else newsAdapter.submitList(it)
            bSeemore.visibility = View.VISIBLE
        }

        binding.clQuestionnaire.setOnClickListener {
            if (sharedPref.isRegistered)
                navController().navigate(R.id.questionnaireFragment)
            else {
                val bundle: Bundle = Bundle()
                bundle.putInt("type", Constants.QUESTIONNAIRE)
                navController().navigate(R.id.signupFragment, bundle)
            }
        }
        bSeemore.setOnClickListener { navController().navigate(R.id.newsFragment) }

    }

    //region
    // endregion
    fun navController() = findNavController()
}
