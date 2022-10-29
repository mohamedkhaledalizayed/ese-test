package com.neqabty.courses.reservations.presentation.view


import android.os.Bundle
import com.example.courses.databinding.ActivityReservationDetailsBinding
import com.neqabty.courses.core.ui.BaseActivity
import com.neqabty.courses.reservations.domain.entity.CourseReservationEntity

class ReservationDetailsActivity : BaseActivity<ActivityReservationDetailsBinding>() {

    override fun getViewBinding() = ActivityReservationDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "تفاصيل الحجز")

        val reservation = intent.getParcelableExtra("reservation") as CourseReservationEntity?
    }
}