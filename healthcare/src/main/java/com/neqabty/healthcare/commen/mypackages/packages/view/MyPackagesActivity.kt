package com.neqabty.healthcare.commen.mypackages.packages.view



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.mypackages.packages.domain.entity.PackageEntity
import com.neqabty.healthcare.commen.mypackages.subscriptiondetails.view.SubscriptionDetailsActivity

@AndroidEntryPoint
class MyPackagesActivity : BaseActivity<ActivityProfileBinding>() {


    private val mAdapter = MyPackagesAdapter()
    private val myPackagesViewModel: MyPackagesViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.packa)

        binding.rvMyPackages.adapter = mAdapter

        mAdapter.onItemClickListener = object : MyPackagesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: PackageEntity) {
                val intent = Intent(this@MyPackagesActivity, SubscriptionDetailsActivity::class.java)
                intent.putExtra("maxFollower", item.maxFollower)
                intent.putExtra("packageId", item.id)
                intent.putExtra("subscriberId", item.subscriberId)
                startActivity(intent)
            }

        }
        myPackagesViewModel.myPackages.observe(this){
            it.let { resource ->

            when(resource.status){
                Status.LOADING ->  {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                    if (resource.data!!.status){
                        mAdapter.submitList(resource.data.data!!.subscribedPackages)
                    }else{

                    }
                }
                Status.ERROR ->{
                    binding.progressCircular.visibility = View.GONE
                    Log.e("ghj", resource.message.toString())
                }
            }

            }
        }

    }

    override fun onResume() {
        super.onResume()
        myPackagesViewModel.getMyPackages(sharedPreferences.mobile)
    }

}