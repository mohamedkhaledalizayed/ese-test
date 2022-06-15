package com.neqabty.healthcare.modules.search.presentation.view.searchresult



import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySearchResultBinding
import com.neqabty.healthcare.modules.search.presentation.view.filter.FilterBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultActivity : BaseActivity<ActivitySearchResultBinding>(), IOnFilterListener {

    private val mAdapter = ItemsAdapter()
    private val searchViewModel: SearchViewModel by viewModels()
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

        searchViewModel.getProviders()
        searchViewModel.providers.observe(this){

            it.let { resource->

                when(resource.status){
                    Status.LOADING ->{
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.progressCircular.visibility = View.GONE
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR ->{
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }

        binding.governmentClose.setOnClickListener {
            binding.governmentContainer.visibility = View.GONE
            binding.cityContainer.visibility = View.GONE
        }

        binding.cityClose.setOnClickListener { binding.cityContainer.visibility = View.GONE }

        binding.searchToolbar.search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(binding.searchToolbar.search.text.toString().isNotEmpty()){
                        return true
                    }else{
                        Toast.makeText(this@SearchResultActivity, "من فضلك ادخل كلمة البحث", Toast.LENGTH_LONG).show()
                    }
                }
                return false
            }
        })
    }

    override fun onFilterClicked(government: String, city: String) {
        binding.governmentContainer.visibility = View.VISIBLE
        binding.cityContainer.visibility = View.VISIBLE

        binding.government.text = government
        binding.city.text = city
    }
}