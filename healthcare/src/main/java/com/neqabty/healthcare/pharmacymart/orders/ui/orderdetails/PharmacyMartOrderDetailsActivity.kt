package com.neqabty.healthcare.pharmacymart.orders.ui.orderdetails


import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPharmacyMartOrderDetailsBinding
import com.neqabty.healthcare.pharmacymart.orders.ui.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog


@AndroidEntryPoint
class PharmacyMartOrderDetailsActivity : BaseActivity<ActivityPharmacyMartOrderDetailsBinding>(), ICancellation {


    private var orderId: String = ""
    private val mAdapter = ItemsAdapter()
    private val mImagesAdapter = ImagesAdapter()
    private lateinit var dialog: AlertDialog
    private val viewModel: OrdersViewModel by viewModels()
    override fun getViewBinding() = ActivityPharmacyMartOrderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setCancelable(false)
            .setMessage(getString(R.string.please_wait))
            .build()

        binding.headerContainer.setOnClickListener { finish() }

        orderId = intent.getIntExtra("orderId", 0).toString()
        binding.productRecyclerView.adapter = mAdapter
        binding.imagesRecyclerView.adapter = mImagesAdapter
        viewModel.getOrder(orderId)
        viewModel.order.observe(this){
            when(it.status){
                Status.LOADING ->{
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.SUCCESS ->{
                    binding.progressCircular.visibility = View.GONE
                    binding.mainContainer.visibility = View.VISIBLE
                    handleOrder(it.data!!.data[0].status)

                    if (it.data.data[0].attachments.isEmpty()){
                        binding.imagesRecyclerView.visibility = View.GONE
                    }else{
                        mImagesAdapter.submitList(it.data.data[0].attachments)
                    }
                    if (it.data.data[0].items.isEmpty()){
                        binding.productRecyclerView.visibility = View.GONE
                    }else{
                        mAdapter.submitList(it.data.data[0].items)
                    }
                    if (it.data.data[0].totalPrice.isEmpty()){
                        binding.itemsDetails.visibility = View.GONE
                    }
                    if (it.data.data[0].orderText.isEmpty()){
                        binding.writeOrder.visibility = View.GONE
                    }
                    if (it.data.data[0].cancellationReason.isEmpty()){
                        binding.cancellationReason.visibility = View.GONE
                    }else{
                        binding.cancellationReason.text = it.data.data[0].cancellationReason
                    }
                    if (it.data.data[0].priceBeforeDiscount.isEmpty()){
                        binding.priceBeforeDiscount.visibility = View.GONE
                    }else{
                        binding.priceBeforeDiscount.text = "السعر قبل الخصم : ${it.data.data[0].priceBeforeDiscount} جنيه"
                    }
                    if (it.data.data[0].priceAfterDiscount.isEmpty()){
                        binding.priceAfterDiscount.visibility = View.GONE
                    }else{
                        binding.priceAfterDiscount.text = "السعر بعد الخصم : ${it.data.data[0].priceAfterDiscount} جنيه"
                    }
                    if (it.data.data[0].deliveryFees.isEmpty()){
                        binding.deliveryFees.visibility = View.GONE
                    }else{
                        binding.deliveryFees.text = "مصاريف التوصيل : ${it.data.data[0].deliveryFees} جنيه"
                    }
                    if (it.data.data[0].totalPrice.isEmpty()){
                        binding.totalPrice.visibility = View.GONE
                    }else{
                        binding.totalPrice.text = "الاجمالى : ${it.data.data[0].totalPrice} جنيه"
                    }
                    binding.orderStatus.text = it.data.data[0].orderStatusTitle
                    binding.writeOrder.text = it.data.data[0].orderText

                    binding.address.text = it.data.data[0].address.let {item->
                        "شارع ${item.streetName}, مبنى رقم ${item.buildingNo}, رقم الطابق ${item.floor}, شقة رقم ${item.apartment}"
                    }
                }
                Status.ERROR ->{
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }

        binding.cancelBtn.setOnClickListener {
            cancelOrder()
        }
        viewModel.cancellationStatus.observe(this){
            when(it.status){
                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    if (it.data!!.status){
                        binding.cancelBtn.visibility = View.GONE
                        binding.confirmBtn.visibility = View.GONE
                        Toast.makeText(this@PharmacyMartOrderDetailsActivity, "تم إالغاء الطلب بنجاح.", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@PharmacyMartOrderDetailsActivity, it.data.message, Toast.LENGTH_LONG).show()
                    }

                }
                Status.ERROR ->{
                    dialog.dismiss()
                    Toast.makeText(this@PharmacyMartOrderDetailsActivity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.confirmBtn.setOnClickListener {
            viewModel.confirmOrder(orderId)
        }
        viewModel.confirmationStatus.observe(this){
            when(it.status){
                Status.LOADING ->{
                    dialog.show()
                }
                Status.SUCCESS ->{
                    dialog.dismiss()
                    if (it.data!!.status){
                        binding.cancelBtn.visibility = View.GONE
                        binding.confirmBtn.visibility = View.GONE
                        Toast.makeText(this@PharmacyMartOrderDetailsActivity, it.data.data, Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@PharmacyMartOrderDetailsActivity, it.data.message, Toast.LENGTH_LONG).show()
                    }
                }
                Status.ERROR ->{
                    dialog.dismiss()
                }
            }
        }
    }

    private fun handleOrder(orderStatus: Int){
        when (orderStatus) {
            1 -> {//Draft
                binding.cancelBtn.visibility = View.VISIBLE
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_new_bg)
            }
            2 -> {//Accepted
                binding.cancelBtn.visibility = View.VISIBLE
                binding.confirmBtn.visibility = View.VISIBLE
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_accepted_bg)
            }
            3 -> {//New
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_inprogress_bg)
            }
            4 -> {//At Warehouse
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_bg)
            }
            5 -> {//With Courier
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_courier_bg)
            }
            6 -> {//Delivered
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_delivered_bg)
            }
            7 -> {//Canceled
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_canceled_bg)
            }
            8 -> {//Returned
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_returened_bg)
            }
            else -> {
                binding.orderStatus.setBackgroundResource(R.drawable.order_status_bg)
            }
        }
    }

    private fun cancelOrder() {
        val fm: FragmentManager = supportFragmentManager
        val dialog = CancellationDialog()
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
    }

    override fun cancelOrder(status: Boolean, reason: String) {
        if (status){
            viewModel.cancelOrder(orderId, reason)
        }
    }
}