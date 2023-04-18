package com.neqabty.healthcare.chefaa.orders.presentation.orderbynote

import android.os.Bundle
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityOrderByNoteBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderByNoteActivity : BaseActivity<ActivityOrderByNoteBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(R.string.order_by_note)

        binding.noteTv.customSelectionActionModeCallback = actionMode
        cart.note?.let { binding.noteTv.setText(it.note) }
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