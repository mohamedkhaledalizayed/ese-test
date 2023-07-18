package com.neqabty.healthcare.news.view.newslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.ActivityNewsListBinding
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.view.newsdetails.NewsDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListActivity : BaseActivity<ActivityNewsListBinding>() {

    private val homeViewModel: NewsViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    private lateinit var imm: InputMethodManager
    override fun getViewBinding() = ActivityNewsListBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.news_title)


//        if (intent.getStringExtra("id") == null || intent.getIntExtra("type", -1) == 1){
//            setupToolbar( titleResId = R.string.general_news)
//            homeViewModel.getAllNews()
//        }else{
//            setupToolbar( titleResId = R.string.news_title)
            homeViewModel.getSyndicateNews(sharedPreferences.code)
//        }

        binding.etSearch.requestFocus()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.etSearch, InputMethodManager.SHOW_IMPLICIT)

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count == 0) {
                    binding.searchBtn.visibility = View.VISIBLE
                    binding.closeBtn.visibility = View.GONE
                } else {
                    binding.closeBtn.visibility = View.VISIBLE
                    binding.searchBtn.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && binding.etSearch.text.toString().isNotEmpty()) {
                hideKeyboard()
            }
            true
        }

        binding.closeBtn.setOnClickListener {
            hideKeyboard()
            binding.etSearch.setText("")
        }


        homeViewModel.news.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.isNotEmpty()){
                            mAdapter.submitList(resource.data)
                        }else{
                            binding.noNewsLayout.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.newsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@NewsListActivity, NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }

    }

}