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
            binding.titleTV.text = it.title
            binding.startDateValue.text = it.startDate
            binding.endDateValue.text = it.endDate
            binding.contactValue.text = it.contact
            binding.peopleValue.text = it.numOfTrainees.toString()
            binding.datesRv.adapter = OfferDatesAdapter(it.appointmentEntities)
            binding.pricingRv.adapter = OfferPricingAdapter(it.pricingEntities) { index ->
                ReservationDialog(this, it, index).show()
            }
        }
    }
}