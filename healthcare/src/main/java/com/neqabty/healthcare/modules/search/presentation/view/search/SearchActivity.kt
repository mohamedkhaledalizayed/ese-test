package com.neqabty.healthcare.modules.search.presentation.view.search




import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySearchBinding
import com.neqabty.healthcare.modules.offers.presentation.view.offers.OffersActivity
import com.neqabty.healthcare.modules.search.presentation.model.search.PackageInfo
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.SearchResultActivity


class SearchActivity : BaseActivity<ActivitySearchBinding>() {


    private val mAdapter = PackagesAdapter()
    override fun getViewBinding() = ActivitySearchBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "نقابتي صحة مستدامة")

        binding.packagesRecycler.adapter = mAdapter
        prepareData()
        mAdapter.onItemClickListener = object :
            PackagesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {

            }
        }

        binding.search.setOnClickListener { startActivity(Intent(this, SearchResultActivity::class.java)) }
        binding.offersContainer.setOnClickListener { startActivity(Intent(this, OffersActivity::class.java)) }
    }

    private fun prepareData(){
        val list = mutableListOf<PackageInfo>()
        list.add(PackageInfo("الباقة الفضية"))
        list.add(PackageInfo("الباقة الذهبية"))
        list.add(PackageInfo("الباقة البلاتينية"))

        mAdapter.submitList(list.toMutableList())
    }
}