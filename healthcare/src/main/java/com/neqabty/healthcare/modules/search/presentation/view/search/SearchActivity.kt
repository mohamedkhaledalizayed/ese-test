package com.neqabty.healthcare.modules.search.presentation.view.search


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySearchBinding
import com.neqabty.healthcare.modules.offers.presentation.view.offers.OffersActivity
import com.neqabty.healthcare.modules.search.presentation.model.filters.ItemUi
import com.neqabty.healthcare.modules.search.presentation.model.search.PackageInfo
import com.neqabty.healthcare.modules.search.presentation.view.filter.FiltersViewModel
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.SearchResultActivity
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.selectedGovernorate
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.selectedProfession
import com.neqabty.healthcare.modules.search.presentation.view.searchresult.selectedProviders
import com.neqabty.healthcare.modules.subscribtions.presentation.view.SubscriptionActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {


    private val filtersViewModel: FiltersViewModel by viewModels()
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
            override fun setOnRegisterClickListener(serviceActionCode: String) {
                val intent = Intent(this@SearchActivity, SubscriptionActivity::class.java)
                intent.putExtra("serviceActionCode", serviceActionCode )
                startActivity(intent)
            }
        }

        if (sharedPreferences.code.isNullOrEmpty()){
            filtersViewModel.getPackages(Constants.NEQABTY_CODE)
        }else{
            filtersViewModel.getPackages(sharedPreferences.code)
        }
        filtersViewModel.packages.observe(this) {
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Log.e("dfghjk", resource.message.toString())
                    }
                }

            }
        }

        binding.offersContainer.setOnClickListener { startActivity(Intent(this, OffersActivity::class.java)) }

        binding.search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(binding.search.text.toString().isNotEmpty()){
                        startActivity(Intent(this@SearchActivity, SearchResultActivity::class.java)
                            .putExtra("name", binding.search.text.toString()))
                        return true
                    }else{
                        Toast.makeText(this@SearchActivity, "من فضلك ادخل كلمة البحث", Toast.LENGTH_LONG).show()
                    }
                }
                return false
            }
        })
    }

    private fun prepareData(){
        val list = mutableListOf<PackageInfo>()
        list.add(PackageInfo("الباقة الفضية"))
        list.add(PackageInfo("الباقة الذهبية"))
        list.add(PackageInfo("الباقة البلاتينية"))

//        mAdapter.submitList(list.toMutableList())
    }
}