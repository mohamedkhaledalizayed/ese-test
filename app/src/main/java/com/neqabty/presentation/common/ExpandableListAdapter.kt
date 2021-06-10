package com.neqabty.presentation.common

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.neqabty.R
import com.neqabty.presentation.entities.NavigationMenuItem


class ExpandableListAdapter(var mContext: Context,
                            var mListDataHeader: List<NavigationMenuItem>,
                            var mListDataChild: HashMap<NavigationMenuItem, List<NavigationMenuItem>>) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        val i = mListDataHeader.size
        return mListDataHeader.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        var childCount = 0
        if (mListDataChild[mListDataHeader[groupPosition]] != null) {
            childCount = mListDataChild[mListDataHeader[groupPosition]]?.size!!
        }
        return childCount
    }

    override fun getGroup(groupPosition: Int): Any {
        return mListDataHeader[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return mListDataChild[mListDataHeader[groupPosition]]!!.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        val headerTitle: NavigationMenuItem = getGroup(groupPosition) as NavigationMenuItem
        if (convertView == null) {
            val infalInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.navigation_menu_item, null)
        }
        val lblListHeader = convertView?.findViewById(R.id.tvTitle) as TextView
        val headerIcon: ImageView = convertView.findViewById(R.id.ivIcon) as ImageView
        lblListHeader.setText(mContext.getString(headerTitle.nameResId))
        headerIcon.setImageResource(headerTitle.iconResId)
        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        val childText = mContext.getString((getChild(groupPosition, childPosition) as NavigationMenuItem).nameResId)
        if (convertView == null) {
            val infalInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.navigation_menu_item, null)
        }
        val txtListChild = convertView?.findViewById(R.id.tvTitle) as TextView
        val childIcon: ImageView = convertView.findViewById(R.id.ivIcon) as ImageView
        txtListChild.text = childText
        childIcon.visibility = View.INVISIBLE
        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }
}