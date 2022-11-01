package com.neqabty.chefaa.modules.orders.presentation.view.orderdetailscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.core.utils.AppUtils
import com.neqabty.chefaa.core.utils.Status
import com.neqabty.chefaa.databinding.ActivityOrderDetailsBinding
import com.neqabty.chefaa.modules.orders.domain.entities.OrderEntity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class OrderDetailsActivity : BaseActivity<ActivityOrderDetailsBinding>() {

    private val orderViewModel: GetOrderViewModel by viewModels()
//    private val mAdapter = ItemssAdapter()
    private lateinit var prescriptionsAdapter: PrescriptionsAdapter
    private lateinit var dialog: AlertDialog
//    private var navigation = false
    private var prescriptionsImages: MutableList<String> = ArrayList()
    override fun getViewBinding() = ActivityOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.order_details)

        val orderId = intent.getParcelableExtra<OrderEntity>("orderId")
//        navigation = intent.getBooleanExtra("navigation", false)
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()
        prescriptionsAdapter = PrescriptionsAdapter(this)
        orderViewModel.getSpecificOrder(orderId?.id.toString())

        orderViewModel.order.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        Toast.makeText(this,resource.data?.size.toString(),Toast.LENGTH_SHORT).show()
                        binding.addressType.text = orderId?.addressId.toString()
                        binding.addressDetails.text = orderId?.deliveryNote.toString()
                        binding.orderNumberValue.text = orderId?.id.toString()
                        binding.dateValue.text = AppUtils().dateFormat(orderId?.createdAt!!)
                        binding.totalPayment.text = "${orderId?.price}  جنيه "

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

//                        if (resource.data.items.isNotEmpty()){
//                            binding.productsRecycler.adapter = mAdapter
//                            mAdapter.submitList(resource.data.items)
//                        }else{
                            binding.productsRecycler.visibility = View.GONE
//                        }


//                        if (resource.data.prescriptionImageEntities!!.isNotEmpty()){
//                            prescriptionsImages.addAll(resource.data.prescriptionImageEntities)
//                            binding.photosRecycler.adapter = prescriptionsAdapter
//                            prescriptionsAdapter.submitList(resource.data.prescriptionImageEntities)
//                        }else{
//                            binding.photosRecycler.visibility = View.GONE
//                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }

        }

        prescriptionsAdapter.onItemClickListener = object :
            PrescriptionsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(position: Int) {
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                val newFragment = SlideshowDialogFragment.newInstance(prescriptionsImages, position)
                newFragment.show(ft, "slideshow")
            }

        }

    }

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
}