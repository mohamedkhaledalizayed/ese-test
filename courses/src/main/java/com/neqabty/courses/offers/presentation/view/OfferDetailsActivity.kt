package com.neqabty.courses.offers.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.courses.R
import com.example.courses.databinding.ActivityOfferDetailsBinding
import com.neqabty.courses.offers.domain.entity.OfferEntity
import com.neqabty.courses.offers.presentation.OfferDatesAdapter
import com.neqabty.courses.offers.presentation.OfferPricingAdapter

const val OFFERDETAILS = "OFFERDETAILS"

class OfferDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOfferDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val offer = intent.getParcelableExtra(OFFERDETAILS) as OfferEntity?
        offer?.let {
            binding.titleTV.text = offer.title
            binding.startDateValue.text = offer.startDate
            binding.endDateValue.text = offer.endDate
            binding.contactValue.text = offer.contact
            binding.peopleValue.text = offer.numOfTrainees.toString()
            binding.datesRv.adapter = OfferDatesAdapter(offer.appointmentEntities)
            binding.pricingRv.adapter = OfferPricingAdapter(offer.pricingEntities)
        }
    }
}