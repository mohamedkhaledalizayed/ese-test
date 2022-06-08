package com.neqabty.healthcare.modules.search.presentation.search



import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySearchBinding


class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    override fun getViewBinding() = ActivitySearchBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "نقابتي صحة مستدامة")
    }
}