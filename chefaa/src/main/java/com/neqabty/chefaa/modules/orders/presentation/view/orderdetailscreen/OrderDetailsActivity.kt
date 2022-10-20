package com.neqabty.chefaa.modules.orders.presentation.view.orderdetailscreen

//import android.app.AlertDialog
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.activity.viewModels
//import androidx.fragment.app.FragmentTransaction
//import com.neqabty.chefaa.R
//import com.neqabty.chefaa.core.data.Constants
//import com.neqabty.chefaa.core.data.Constants.jwt
//import com.neqabty.chefaa.core.data.Constants.mobileNumber
//import com.neqabty.chefaa.core.data.Constants.userNumber
//import com.neqabty.chefaa.core.ui.BaseActivity
//import com.neqabty.chefaa.core.utils.AppUtils
//import com.neqabty.chefaa.core.utils.Status
//import com.neqabty.chefaa.databinding.ActivityOrderDetailsBinding
//import com.neqabty.chefaa.modules.home.presentation.homescreen.HomeActivity
//import dagger.hilt.android.AndroidEntryPoint
//import dmax.dialog.SpotsDialog
//
//@AndroidEntryPoint
//class OrderDetailsActivity : BaseActivity<ActivityOrderDetailsBinding>() {
//
//    private val orderViewModel: GetOrderViewModel by viewModels()
//    private val mAdapter = ItemssAdapter()
//    private lateinit var prescriptionsAdapter: PrescriptionsAdapter
//    private lateinit var dialog: AlertDialog
//    private var navigation = false
//    private var prescriptionsImages: MutableList<String> = ArrayList()
//    override fun getViewBinding() = ActivityOrderDetailsBinding.inflate(layoutInflater)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        setupToolbar(titleResId = R.string.order_details)
//
//        val orderId = intent.getStringExtra("orderId")
//        navigation = intent.getBooleanExtra("navigation", false)
//        dialog = SpotsDialog.Builder()
//            .setContext(this)
//            .setMessage(getString(R.string.please_wait))
//            .build()
//        prescriptionsAdapter = PrescriptionsAdapter(this)
//        orderViewModel.getSpecificOrder(mobileNumber, "$orderId")
//
//        orderViewModel.order.observe(this){
//            it?.let { resource ->
//                when (resource.status) {
//                    Status.LOADING -> {
//                        dialog.show()
//                    }
//                    Status.SUCCESS -> {
//                        dialog.dismiss()
//                        binding.addressType.text = resource.data?.address?.addressName
//                        binding.addressDetails.text = resource.data?.address?.address
//                        binding.orderNumberValue.text = resource.data?.orderNumber
//                        binding.dateValue.text = AppUtils().dateFormat(resource.data?.creationDate!!)
//                        binding.totalPayment.text = "${resource.data?.orderPrice} جنيه"
//
//                        when (resource.data.currentStatus) {
//                            "New" -> {
//                                binding.orderStatusValue.text = "لقد تم تنفيذ طلبك بنجاح"
//                                binding.orderStatusValue.setTextColor(resources.getColor(R.color.black))
//                            }
//                            "Dispatched" -> {
//                                binding.orderStatusValue.text = "لقد تم تسليم هذا الطلب"
//                                binding.orderStatusValue.setTextColor(resources.getColor(R.color.green))
//                            }
//                            else -> {
//                                binding.orderStatusValue.text = "تم الغاء هذا الطلب"
//                                binding.orderStatusValue.setTextColor(resources.getColor(R.color.red))
//                            }
//                        }
//
//                        if (resource.data.items.isNotEmpty()){
//                            binding.productsRecycler.adapter = mAdapter
//                            mAdapter.submitList(resource.data.items)
//                        }else{
//                            binding.productsRecycler.visibility = View.GONE
//                        }
//
//
//                        if (resource.data.prescriptionImageEntities!!.isNotEmpty()){
//                            prescriptionsImages.addAll(resource.data.prescriptionImageEntities)
//                            binding.photosRecycler.adapter = prescriptionsAdapter
//                            prescriptionsAdapter.submitList(resource.data.prescriptionImageEntities)
//                        }else{
//                            binding.photosRecycler.visibility = View.GONE
//                        }
//                    }
//                    Status.ERROR -> {
//                        dialog.dismiss()
//                    }
//                }
//            }
//
//        }
//
//        prescriptionsAdapter.onItemClickListener = object :
//            PrescriptionsAdapter.OnItemClickListener {
//            override fun setOnItemClickListener(position: Int) {
//                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
//                val newFragment = SlideshowDialogFragment.newInstance(prescriptionsImages, position)
//                newFragment.show(ft, "slideshow")
//            }
//
//        }
//
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        if (navigation){
//            val bundle = Bundle()
//            bundle.putString("user_number", userNumber)
//            bundle.putString("mobile_number", mobileNumber)
//            bundle.putString("jwt", jwt)
//            val intent = Intent(this, HomeActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.putExtras(bundle)
//            startActivity(intent)
//            finish()
//        }else{
//            finish()
//        }
//    }
//}