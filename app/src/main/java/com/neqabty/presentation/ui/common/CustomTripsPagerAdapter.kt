package com.neqabty.presentation.ui.common

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.neqabty.R
import com.neqabty.presentation.entities.TripUI
import com.neqabty.presentation.util.DisplayMetrics

class CustomTripsPagerAdapter(val context: Context, val trips: List<TripUI>) : PagerAdapter() {
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p1 == p0
    }

    override fun getCount(): Int {
        return trips.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.trips_card, null)
        var clHolder = view.findViewById<ConstraintLayout>(R.id.clHolder)
        val layoutParams = ConstraintLayout.LayoutParams(DisplayMetrics.width / 4, DisplayMetrics.width / 4)
        clHolder.layoutParams = layoutParams
        var imageView = view.findViewById<ImageView>(R.id.ivLogo)
//        Glide.with(context).load(Uri.parse(trips[position].img)).into(imageView)
        var tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = trips[position].title

//        imageView.setTag(R.id.ivLogo, position); // "i" means some integer value
//        imageView.setTag(R.id.ivLogo, position)
//        if (imageView.getTag(R.id.ivLogo) == position)
//            Glide.with(context).load(Uri.parse(trips[position].img)).into(imageView)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}