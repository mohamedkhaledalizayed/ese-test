package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivitySearchBinding
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    private val productViewModel: ProductViewModel by viewModels()
    private val mAdapter = SearchAdapter()
    override fun getViewBinding() = ActivitySearchBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.search)

        binding.recyclerView.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            SearchAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: ProductEntity) {
                val intent: Intent = Intent(this@SearchActivity, ProductDetailsActivity::class.java)
                intent.putExtra("product", item)
                startActivity(intent)
            }
        }

        binding.llHolder.findViewById<ImageView>(R.id.search_btn).setOnClickListener {
            hideKeyboard()
            findViewById<ProgressRelativeLayout>(R.id.progressActivity).showLoading()
            productViewModel.search(binding.llHolder.findViewById<EditText>(R.id.et_search).text.toString())
            productViewModel.products.observe(this) {
                findViewById<ProgressRelativeLayout>(R.id.progressActivity).showContent()
                mAdapter.clear()
                mAdapter.submitList(it)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        val cartItem = menu.findItem(R.id.menu_item_cart)
        cartItem.actionView.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
        cartItem.actionView.findViewById<TextView>(R.id.tv_count).visibility = if(Constants.cartItems.size == 0) View.INVISIBLE else View.VISIBLE
        cartItem.actionView.findViewById<TextView>(R.id.tv_count).text = Constants.cartItems.size.toString()
        return super.onCreateOptionsMenu(menu)
    }
}
