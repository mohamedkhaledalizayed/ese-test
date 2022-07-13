package com.neqabty.healthcare.modules.suggestions.presentation


import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySuggestionsBinding

class SuggestionsActivity : BaseActivity<ActivitySuggestionsBinding>() {

    override fun getViewBinding() = ActivitySuggestionsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "الشكاوي والمقترحات")
    }
}