package com.neqabty.courses.reservations.presentation.view



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.courses.databinding.ActivityReservationsBinding
import com.neqabty.courses.core.ui.BaseActivity
import com.neqabty.courses.core.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationsActivity : BaseActivity<ActivityReservationsBinding>() {

    override fun getViewBinding() = ActivityReservationsBinding.inflate(layoutInflater)
    private val reservationsViewModel: ReservationsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "الحجوزات")

        reservationsViewModel.getReservations("+201128236544")

        val adapter = ReservationsAdapter(){
            val intent = Intent(this, ReservationDetailsActivity::class.java)
            intent.putExtra("reservation", it)
            startActivity(intent)
        }

        binding.reservations.adapter = adapter
        reservationsViewModel.reservations.observe(this){
            it.let { resource ->

            when(resource.status){
                Status.LOADING->{
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.SUCCESS->{
                    binding.progressCircular.visibility = View.GONE
                    adapter.submitList(resource.data!!)
                }
                Status.ERROR->{
                    Log.e("fghj", resource.message.toString())
                    binding.progressCircular.visibility = View.GONE
                }
            }
            }

        }
    }
}