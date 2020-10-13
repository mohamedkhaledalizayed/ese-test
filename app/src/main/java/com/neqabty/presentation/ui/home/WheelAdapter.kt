package com.neqabty.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.neqabty.R
import com.neqabty.presentation.util.DisplayMetrics
import github.hellocsl.cursorwheel.CursorWheelLayout.CycleWheelAdapter


class WheelAdapter(var context: Context, var sectionsList: List<String>, var iconsList: List<Int>, var selectedItemPosition: Int) : CycleWheelAdapter() {
    override fun getCount(): Int {
        return iconsList.size
    }

    override fun getView(parent: View?, position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.wheel_item, null)
        val clHolder = view.findViewById<ConstraintLayout>(R.id.clHolder)
        val ivSrc = view.findViewById<ImageView>(R.id.ivSrc)
        val ivBg = view.findViewById<ImageView>(R.id.ivBg)

        view.rootView.layoutParams = ViewGroup.LayoutParams(DisplayMetrics.width / 5, DisplayMetrics.width / 5)

        if (position == selectedItemPosition) {
            ivBg.setImageResource(R.drawable.bg_green_circle)
            clHolder.layoutParams = ConstraintLayout.LayoutParams(DisplayMetrics.width / 5 - 3, DisplayMetrics.width / 5 - 3)
        }
        else{
            ivBg.setBackgroundResource(R.drawable.bg_blue_circle)
            clHolder.layoutParams = ConstraintLayout.LayoutParams(DisplayMetrics.width / 5 - 20, DisplayMetrics.width / 5 - 20)
        }
        ivSrc.setImageResource(iconsList[position])

        return view
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any {
        return sectionsList[position]
    }
}
