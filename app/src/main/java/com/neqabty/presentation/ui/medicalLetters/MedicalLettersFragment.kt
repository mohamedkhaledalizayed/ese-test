package com.neqabty.presentation.ui.medicalLetters

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalLettersFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.LiteFollowersListUI
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.medical_letters_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MedicalLettersFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalLettersFragmentBinding>()
    private var adapter by autoCleared<MedicalLettersAdapter>()

    private val medicalLetterViewModel: MedicalLettersViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors
    var liteFollowersListUI: List<LiteFollowersListUI>? = listOf<LiteFollowersListUI>()

    lateinit var selectedFollower: LiteFollowersListUI

    private var isLoading = true
    private var pageNumber = 0
    private var itemsPerPage = 8
    private var pastVisibleItem: Int = 0
    var visibleItemsCount: Int = 0
    var totalItemsCount: Int = 0
    var previousTotal = 0
    private var threshold = 2
    lateinit var mLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_letters_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        sharedPref.user = "3608662"
        initializeViews()

        val adapter = MedicalLettersAdapter(dataBindingComponent, appExecutors) { letterItem ->
//            navController().navigate(
//                    MedicalLettersFragmentDirections.newsDetails(newsItem)
//            )
        }
        this.adapter = adapter
        binding.rvLetters.adapter = adapter

        medicalLetterViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalLetterViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalLetterViewModel.validateUser(sharedPref.user)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        medicalLetterViewModel.validateUser(sharedPref.user)
    }

    private fun handleViewState(state: MedicalLettersViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        llContent.visibility = if (state.isLoading) View.INVISIBLE else View.VISIBLE

        state.medicalLetterUI?.let {
            if(it.letters?.size == 0 ) {
                binding.tvNoDataFound.visibility = View.VISIBLE
            } else {
                binding.tvNoDataFound.visibility = View.GONE
                adapter.submitList(it.letters)
                adapter.notifyDataSetChanged()
            }
            return
        }

        state.liteFollowersListUI?.let {
            liteFollowersListUI = it
            renderFollowers()
            return
        }

        if (state.member != null) {
            when (state.member?.code) {
                0 -> {
                    llSuperProgressbar.visibility = View.VISIBLE
                    state.member = null
                    medicalLetterViewModel.getMedicalRenewalData(sharedPref.mobile, sharedPref.user)
                }
                else -> {
                    if (state.member?.message != null) showAlert(state.member?.message ?: getString(R.string.user_not_allowed)){navController().navigateUp()}
                    state.member?.message = null
                }
            }
        }


    }

    fun initializeViews() {
        mLayoutManager = LinearLayoutManager(context)
        binding.rvLetters.layoutManager = mLayoutManager
        binding.rvLetters.addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        visibleItemsCount = mLayoutManager.childCount
                        totalItemsCount = mLayoutManager.itemCount
                        pastVisibleItem = mLayoutManager.findFirstVisibleItemPosition()

                        if (dy > 0) {
                            if (isLoading) {
                                if (totalItemsCount > previousTotal) { // da el stopping condition, el condition da 3shan a turn on el isloading lma el items tzed m3na kda en fe data gt tany
                                    isLoading = false
                                    previousTotal = totalItemsCount
                                }
                            }

                            if (!isLoading && (totalItemsCount - visibleItemsCount) <= (pastVisibleItem + threshold)) { // el data gt w el unseen items b2t <= el threshold
                                pageNumber += 1
                                isLoading = true
                                loadMedicalLetters(1 + pageNumber*itemsPerPage , (pageNumber + 1) * itemsPerPage)
                            }
                        }
                    }
                })
    }

    fun loadMedicalLetters(start: Int = 0, end: Int = 0){
        llSuperProgressbar.visibility = View.VISIBLE
        medicalLetterViewModel.getMedicalLetters(selectedFollower.id.toString(), start, end, mobileNumber = sharedPref.mobile)
    }
//region

    fun renderFollowers() {
        binding.spFollower.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, liteFollowersListUI!!)
        binding.spFollower.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                binding.tvNoDataFound.visibility = View.GONE
                selectedFollower = parent.getItemAtPosition(position) as LiteFollowersListUI
                resetPagination()
                loadMedicalLetters(0, itemsPerPage)
            }
        }
        binding.spFollower.setSelection(0)
    }

    private fun resetPagination(){
        pageNumber = 0
        pastVisibleItem = 0
        visibleItemsCount = 0
        totalItemsCount = 0
        previousTotal = 0
    }
// endregion

    fun navController() = findNavController()
}
