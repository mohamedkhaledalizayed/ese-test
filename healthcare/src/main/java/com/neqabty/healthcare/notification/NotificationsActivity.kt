package com.neqabty.healthcare.notification


import android.os.Bundle
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsActivity : BaseActivity<ActivityNotificationsBinding>() {

    private var mAdapter: NotificationsAdapter = NotificationsAdapter()
    override fun getViewBinding() = ActivityNotificationsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "الاشعارات")

        binding.notificationsRecycler.adapter = mAdapter

        mAdapter.onItemClickListener = object : NotificationsAdapter(),
            NotificationsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {

            }

        }

    }
}