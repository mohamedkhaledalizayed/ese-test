package com.neqabty.healthcare.commen.contact_providers.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityContactProvidersBinding
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.SearchBody
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.providerdetails.ProviderDetailsActivity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.ItemsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactProvidersActivity : BaseActivity<ActivityContactProvidersBinding>() {
    private val mAdapter = ItemsAdapter()
    private val contactProvidersViewModel: ContactProvidersViewModel by viewModels()

    private var pageNumber = 1
    private var isLoading = true
    private var pastVisibleItem: Int = 0
    var visibleItemsCount: Int = 0
    var totalItemsCount: Int = 0
    var previousTotal = 0
    private var threshold = 4
    lateinit var mLayoutManager: LinearLayoutManager
    override fun getViewBinding() = ActivityContactProvidersBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(R.string.contact_providers)
        observeOnContactProvidersStatus()
        getAllProviders()


        mLayoutManager = LinearLayoutManager(this)
        binding.rvProviders.layoutManager = mLayoutManager
        binding.rvProviders.itemAnimator = DefaultItemAnimator()
        binding.rvProviders.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            ItemsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: ProvidersEntity) {
                val intent = Intent(this@ContactProvidersActivity, ProviderDetailsActivity::class.java)
                intent.putExtra("provider", item)
                startActivity(intent)
            }
        }


        binding.rvProviders.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                        pagination()
                    }
                }
            }
        })
    }

    private fun getAllProviders() {
        mAdapter.clear()
        pageNumber = 1
        isLoading = true
        pastVisibleItem = 0
        visibleItemsCount = 0
        totalItemsCount = 0
        previousTotal = 0
        pagination()
    }

    private fun pagination() {
        contactProvidersViewModel.getProviders(SearchBody(page = pageNumber, has_qrcode = 1))
    }

    private fun observeOnContactProvidersStatus() {
        contactProvidersViewModel.providers.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
                        hideProgressDialog()
                        if (resource.data != null) {
                            mAdapter.submitList(resource.data?.toMutableList())
                        }
                    }
                    Status.ERROR -> {
                        hideProgressDialog()
                        showAlert(message = resource.message ?: "") { }
                    }
                }
            }
        }
    }

    //region
// endregion
}