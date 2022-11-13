package com.neqabty.chefaa.modules.orders.presentation.orderbynote

import android.os.Bundle
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.databinding.ActivityOrderByNoteBinding
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity

class OrderByNoteActivity : BaseActivity<ActivityOrderByNoteBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(R.string.order_by_note)
        binding.saveBtn.setOnClickListener {
            if (binding.noteTv.text.toString().isNullOrBlank())
                return@setOnClickListener
            cart.note = OrderItemsEntity(
                type = Constants.ITEMTYPES.NOTE.typeName,
                quantity = 1,
                image = "",
                note = binding.noteTv.text.toString(),
                productId = -1,
                productEntity = null,
                imageUri = null
            )
            finish()
        }
    }

    override fun getViewBinding() = ActivityOrderByNoteBinding.inflate(layoutInflater)
}