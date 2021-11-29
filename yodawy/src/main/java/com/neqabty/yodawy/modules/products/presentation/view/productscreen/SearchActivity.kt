package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.imageList
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.Status
import com.neqabty.yodawy.databinding.ActivitySearchBinding
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    private val productViewModel: ProductViewModel by viewModels()
    lateinit var mAdapter: SearchAdapter
    override fun getViewBinding() = ActivitySearchBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.search)

        mAdapter = SearchAdapter {
            invalidateOptionsMenu()
        }

        binding.recyclerView.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            SearchAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: ProductEntity) {
                val intent: Intent = Intent(this@SearchActivity, ProductDetailsActivity::class.java)
                intent.putExtra("product", item)
                startActivity(intent)
            }

            override fun setOnAddItemClickListener(confirmClearCartCallback: () -> Unit) {
                if (imageList.isNotEmpty()){
                    showClearCartConfirmationAlert(okCallback = {
                        imageList.clear()
                        invalidateOptionsMenu()
                        confirmClearCartCallback.invoke()
                    })
                }
            }
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
            }
            true
        }

        binding.searchBtn.setOnClickListener {
            search()
        }

        //observe
        productViewModel.products.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressActivity.showLoading()
                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.isEmpty()){
                            binding.progressActivity.showEmpty(R.drawable.ic_no_data_found, "فارغ", "لا يوجد نتائج البحث")
                        }else{
                            binding.progressActivity.showContent()
                            mAdapter.clear()
                            mAdapter.submitList(resource.data)
                            binding.recyclerView.scrollToPosition(0)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressActivity.showEmpty(R.drawable.ic_no_data_found, "خطا", resource.message)
                    }
                }
            }

        }

        binding.etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 0){
                    binding.searchBtn.visibility = View.VISIBLE
                    binding.closeBtn.visibility = View.GONE
                }else{
                    binding.closeBtn.visibility = View.VISIBLE
                    binding.searchBtn.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        binding.closeBtn.setOnClickListener {
            binding.etSearch.setText("")
        }

    }

    private fun search(){
        hideKeyboard()
        productViewModel.search(binding.llHolder.findViewById<EditText>(R.id.et_search).text.toString())
    }

    override fun onResume() {
        super.onResume()
        if (!binding.llHolder.findViewById<EditText>(R.id.et_search).text.toString().isNullOrEmpty()){
            productViewModel.search(binding.llHolder.findViewById<EditText>(R.id.et_search).text.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        val cartItem = menu.findItem(R.id.menu_item_cart)
        updateCartOptionsMenu(cartItem)

        return super.onCreateOptionsMenu(menu)
    }
}
