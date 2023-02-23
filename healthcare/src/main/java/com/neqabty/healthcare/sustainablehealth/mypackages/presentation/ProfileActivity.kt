package com.neqabty.healthcare.sustainablehealth.mypackages.presentation


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile.SubscribedPackageEntity
import com.neqabty.healthcare.sustainablehealth.payment.view.SehaPaymentActivity
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view.SubscriptionActivity

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {


    private var userNumber: String? = ""
    private val mAdapter = PackagesAdapter()
    private val profileViewModel: ProfileViewModel by viewModels()
    override fun getViewBinding() = ActivityProfileBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.packa)


        binding.followersRecycler.adapter = mAdapter


        profileViewModel.userData.observe(this){
            it.let { resource ->

            when(resource.status){
                Status.LOADING ->  {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.layoutContainer.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                    if (resource.data!!.status){
                        binding.layoutContainer.visibility = View.VISIBLE
                        mAdapter.submitList(resource.data.data!!.subscribedPackages)
                        userNumber = resource.data.data.client.userNumber
//                        binding.qrCode.loadSVG(resource.data!!.data.client.qrCode)
                    }else{
                        binding.noPackagesLayout.visibility = View.VISIBLE
                    }
                }
                Status.ERROR ->{
                    binding.progressCircular.visibility = View.GONE
                    Log.e("dfghjk", "${resource.message}")
                }
            }

            }
        }

        mAdapter.onItemClickListener = object :
            PackagesAdapter.OnItemClickListener {
            override fun setOnDeleteItemClickListener(subscriberId: String, followerId: Int) {
                deleteFollower("هل تريد حذف هذا التابع !", followerId, subscriberId)
            }

            override fun setOnAddItemClickListener(packageId: String, subscriberId: String, isMaxFollower: Boolean) {
                if (isMaxFollower){
                    Toast.makeText(this@ProfileActivity, "لقد وصلت الى الحد الاقصى فى إضافة التابعين", Toast.LENGTH_LONG).show()
                    return
                }
                addFollower(packageId, subscriberId)
            }

            override fun setOnPayClickListener(item: SubscribedPackageEntity) {
                val intent = Intent(this@ProfileActivity, SehaPaymentActivity::class.java)
                Log.e("price", item.packages.packagePrice ?: "0")
                intent.putExtra("name", item.packages.nameAr)
                intent.putExtra("price", item.packages.packagePrice?.toDouble())
                intent.putExtra("vat", item.packages.vat?.toDouble())
                intent.putExtra("total", item.packages.total?.toDouble())
                intent.putExtra("serviceCode", item.packages.serviceCode)
                intent.putExtra("serviceActionCode", item.packages.serviceActionCode)
                startActivity(intent)
            }

            override fun setOnEditClickListener() {
                val intent = Intent(this@ProfileActivity, SubscriptionActivity::class.java)
                intent.putExtra("subscriptionMode", false )
                intent.putExtra("userNumber", userNumber )
                startActivity(intent)
            }
        }

        profileViewModel.followerStatus.observe(this){
            it.let { resource ->

                when(resource.status){
                    Status.LOADING ->{
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!){
                            profileViewModel.getProfile(sharedPreferences.mobile)
                            Toast.makeText(this@ProfileActivity, "تم حذف التابع بنجاح.", Toast.LENGTH_LONG).show()
                            onResume()
                        }else{
                            Toast.makeText(this@ProfileActivity, "لم يتم حذف التابع.", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR ->{
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this@ProfileActivity, resource.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

    }

    private fun deleteFollower(message: String, followerId: Int, subscriberId: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            profileViewModel.deleteFollower(followerId, subscriberId)
            dialog.dismiss()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getProfile(sharedPreferences.mobile)
    }

    private fun addFollower(packageId: String, subscriberId: String) {
        val intent = Intent(this, AddFollowerActivity::class.java)
        intent.putExtra("packageId", packageId)
        intent.putExtra("subscriberId", subscriberId)
        startActivity(intent)
    }

}