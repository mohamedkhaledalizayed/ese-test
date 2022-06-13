package com.neqabty.healthcare.modules.search.presentation.view.search




import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySearchBinding
import com.neqabty.healthcare.modules.offers.presentation.view.offers.OffersActivity
import com.neqabty.healthcare.modules.register.presentation.RegistrationActivity
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
                startActivity(Intent(this@SearchActivity, RegistrationActivity::class.java))
            }

            override fun setOnRegisterClickListener(item: String) {
                startActivity(Intent(this@SearchActivity, RegistrationActivity::class.java))
            }
        }

        binding.offersContainer.setOnClickListener { startActivity(Intent(this, OffersActivity::class.java)) }

        binding.search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(binding.search.text.toString().isNotEmpty()){
                        startActivity(Intent(this@SearchActivity, SearchResultActivity::class.java))
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

        mAdapter.submitList(list.toMutableList())
    }
}