package com.neqabty.healthcare.sustainablehealth.search.presentation.view.providerdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.core.ui.BaseDialogFragment
import com.neqabty.healthcare.databinding.FragmentAddReviewDailogBinding


class AddReviewDailog : BaseDialogFragment<FragmentAddReviewDailogBinding>() {

    override fun getViewBinding() = FragmentAddReviewDailogBinding.inflate(layoutInflater)

    private var rate: Float = 0.0f
    private var reviewContent: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.animation.playAnimation()

        binding.review.setOnClickListener {
            rate = binding.rateBar.rating
            reviewContent = binding.comment.text.toString()

            if (rate == 0.0f) {
                Toast.makeText(context, "من فضلك قيم مقدم الخدمة اولا", Toast.LENGTH_LONG).show()
            } else {
                val recipeDetailsActivity = activity as ProviderDetailsActivity
                dismiss()
            }
        }
    }
}