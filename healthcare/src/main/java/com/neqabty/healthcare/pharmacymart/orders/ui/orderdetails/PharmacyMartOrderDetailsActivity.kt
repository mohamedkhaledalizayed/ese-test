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
import com.neqabty.healthcare.core.ui.ExitDialog
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityPharmacyMartOrderDetailsBinding
import com.neqabty.healthcare.pharmacymart.orders.ui.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog


@AndroidEntryPoint
class PharmacyMartOrderDetailsActivity : BaseActivity<ActivityPharmacyMartOrderDetailsBinding>(), ICancellation {


    private var orderId: String = ""
    private val mAdapter = ItemsAdapter()
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

        orderId = intent.getIntExtra("orderId", 0).toString()
        binding.productRecyclerView.adapter = mAdapter
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
                    mAdapter.submitList(it.data.data[0].items)
                    binding.total.text = "${it.data.data[0].priceBeforeDiscount} جنيه"
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

                }
                Status.SUCCESS ->{

                }
                Status.ERROR ->{

                }
            }
        }
    }

    private fun handleOrder(orderStatus: Int){
        when (orderStatus) {
            1 -> {//Draft
                binding.cancelBtn.visibility = View.VISIBLE
            }
            2 -> {//Accepted
                binding.cancelBtn.visibility = View.VISIBLE
                binding.confirmBtn.visibility = View.VISIBLE
            }
            3 -> {//New

            }
            4 -> {//At Warehouse

            }
            5 -> {//With Courier

            }
            6 -> {//Delivered

            }
            7 -> {//Canceled

            }
            8 -> {//Returned

            }
            else -> {

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