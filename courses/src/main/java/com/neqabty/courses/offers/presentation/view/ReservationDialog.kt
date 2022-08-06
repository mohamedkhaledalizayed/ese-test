package com.neqabty.courses.offers.presentation.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.example.courses.databinding.DialogReservationBinding
import com.neqabty.courses.offers.domain.entity.OfferEntity

class ReservationDialog(context: Context, val offerEntity: OfferEntity, val pricingIndex: Int) :
    Dialog(context) {
    private lateinit var binding: DialogReservationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.titleTV.text = offerEntity.title
        binding.priceTv.text = offerEntity.pricingEntities[pricingIndex].price
        binding.studentTypeTv.text =
            offerEntity.pricingEntities[pricingIndex].studentCategoryEntity.name
        this.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}