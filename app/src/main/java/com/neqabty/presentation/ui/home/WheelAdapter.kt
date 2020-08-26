package com.neqabty.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.neqabty.R
import github.hellocsl.cursorwheel.CursorWheelLayout.CycleWheelAdapter


class WheelAdapter(var context: Context, var sectionsList: List<String>, var iconsList: List<Int>, var selectedItemPosition: Int) : CycleWheelAdapter() {
    override fun getCount(): Int {
        return iconsList.size
    }

    override fun getView(parent: View?, position: Int): View {
        val view = LayoutInflater.from(context).inflate(R.layout.wheel_item, null)
        val ivSrc = view.findViewById<ImageView>(R.id.ivSrc)
        val ivBg = view.findViewById<ImageView>(R.id.ivBg)
        if (position == selectedItemPosition)
            ivBg.setImageResource(R.drawable.bg_green_circle)
        else
            ivBg.setBackgroundResource(R.drawable.bg_blue_circle)
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
