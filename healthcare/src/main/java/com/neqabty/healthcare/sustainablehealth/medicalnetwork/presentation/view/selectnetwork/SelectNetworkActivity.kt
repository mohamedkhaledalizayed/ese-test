package com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.selectnetwork



import android.content.Intent
import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySelectNetworkBinding
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.SearchResultActivity

class SelectNetworkActivity : BaseActivity<ActivitySelectNetworkBinding>() {

    override fun getViewBinding(): ActivitySelectNetworkBinding = ActivitySelectNetworkBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            startActivity(Intent(this, SearchResultActivity::class.java))
        }
        binding.headerContainer.setOnClickListener { finish() }
    }

}