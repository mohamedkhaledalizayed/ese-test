package com.neqabty.healthcare.modules.search.presentation.view.searchresult


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.neqabty.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySearchResultBinding
import com.neqabty.healthcare.modules.search.data.model.SearchBody
import com.neqabty.healthcare.modules.search.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.modules.search.presentation.model.filters.ItemUi
import com.neqabty.healthcare.modules.search.presentation.view.filter.FilterBottomSheet
import com.neqabty.healthcare.modules.search.presentation.view.providerdetails.ProviderDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
var selectedGovernorate = 0
var selectedProfession = 0
var selectedProviders = 0
var selectedDegree = 0

@AndroidEntryPoint
class SearchResultActivity : BaseActivity<ActivitySearchResultBinding>(), IOnFilterListener {

    private var governorateId = ""
    private var serviceProviderTypeId = ""
    private var name = ""
    private var professionId = ""
    private var degree = ""
    private val mAdapter = ItemsAdapter()
    private val searchViewModel: SearchViewModel by viewModels()
    override fun getViewBinding() = ActivitySearchResultBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "البحث في الشبكة الصحية")

        name = intent.getStringExtra("name") ?: ""
        binding.searchToolbar.search.setText(name)
        binding.itemsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            ItemsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: ProvidersEntity) {
                val intent = Intent(this@SearchResultActivity, ProviderDetailsActivity::class.java)
                intent.putExtra("name", item.name)
                startActivity(intent)
            }
        }

        binding.searchToolbar.filter.setOnClickListener {
            val bottomSheetFragment = FilterBottomSheet()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        searchViewModel.getProviders(SearchBody(governorateId, serviceProviderTypeId, name, professionId))
        searchViewModel.providers.observe(this) {

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
                    }
                }

            }
        }

        binding.governmentClose.setOnClickListener {
            binding.governmentContainer.visibility = View.GONE
            governorateId = ""
            selectedGovernorate = 0
            search()
        }

        binding.professionClose.setOnClickListener {
            binding.professionContainer.visibility = View.GONE
            professionId = ""
            selectedProfession = 0
            search()
        }
        binding.typeClose.setOnClickListener {
            binding.typeContainer.visibility = View.GONE
            serviceProviderTypeId = ""
            selectedProviders = 0
            search()
        }
        binding.degreesClose.setOnClickListener {
            binding.degreesContainer.visibility = View.GONE
            degree = ""
            selectedDegree = 0
            search()
        }
        binding.searchToolbar.search.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search()
                    return true
                }
                return false
            }
        })

        binding.searchToolbar.searchBtn.setOnClickListener {
            search()
        }

        binding.searchToolbar.closeBtn.setOnClickListener {
            binding.searchToolbar.search.setText("")
        }

        binding.searchToolbar.search.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = binding.searchToolbar.search.text.toString()
                if (count == 0){
                    binding.searchToolbar.searchBtn.visibility = View.VISIBLE
                    binding.searchToolbar.closeBtn.visibility = View.GONE
                }else{
                    binding.searchToolbar.closeBtn.visibility = View.VISIBLE
                    binding.searchToolbar.searchBtn.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                search()
            }

        })
    }

    private fun search() {
        searchViewModel.getProviders(SearchBody(governorateId, serviceProviderTypeId, name, professionId))
    }

    override fun onResume() {
        super.onResume()
        if (binding.searchToolbar.search.text.toString().isNotEmpty()){
            binding.searchToolbar.closeBtn.visibility = View.VISIBLE
            binding.searchToolbar.searchBtn.visibility = View.GONE
        }
    }

    override fun onFilterClicked(government: ItemUi?, profession: ItemUi?, providerType: ItemUi?, degrees: ItemUi?) {
        government?.let {
            binding.governmentContainer.visibility = View.VISIBLE
            governorateId = it.id.toString()
            binding.government.text = it.name
        } ?: run {
            binding.governmentContainer.visibility = View.GONE
        }

        profession?.let {
            binding.professionContainer.visibility = View.VISIBLE
            professionId = it.id.toString()
            binding.profession.text = it.name
        } ?: run {
            binding.professionContainer.visibility = View.GONE
        }

        providerType?.let {
            binding.typeContainer.visibility = View.VISIBLE
            serviceProviderTypeId = it.id.toString()
            binding.type.text = it.name
        } ?: run {
            binding.typeContainer.visibility = View.GONE
        }

        degrees?.let {
            binding.degreesContainer.visibility = View.VISIBLE
            degree = it.id.toString()
            binding.degrees.text = it.name
        } ?: run {
            binding.degreesContainer.visibility = View.GONE
        }
        search()

    }
}