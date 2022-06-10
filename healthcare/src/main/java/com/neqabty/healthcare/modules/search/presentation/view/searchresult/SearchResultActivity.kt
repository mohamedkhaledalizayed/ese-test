package com.neqabty.healthcare.modules.search.presentation.view.searchresult



import android.os.Bundle
import android.view.View
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySearchResultBinding
import com.neqabty.healthcare.modules.search.presentation.view.filter.FilterBottomSheet
import com.neqabty.healthcare.modules.search.presentation.view.search.PackagesAdapter

class SearchResultActivity : BaseActivity<ActivitySearchResultBinding>(), IOnFilterListener {

    private val mAdapter = ItemsAdapter()
    override fun getViewBinding() = ActivitySearchResultBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "البحث في الشبكة الصحية")

        binding.itemsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            ItemsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {

            }
        }

        binding.searchToolbar.filter.setOnClickListener {
            val bottomSheetFragment = FilterBottomSheet()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

    }

    override fun onFilterClicked(government: String, city: String) {
        binding.governmentContainer.visibility = View.VISIBLE
        binding.cityContainer.visibility = View.VISIBLE

        binding.government.text = government
        binding.city.text = city
    }
}