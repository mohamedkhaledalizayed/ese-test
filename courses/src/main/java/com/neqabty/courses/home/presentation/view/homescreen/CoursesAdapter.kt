package com.neqabty.courses.home.presentation.view.homescreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.courses.R
import com.example.courses.databinding.CourseItemBinding
import com.neqabty.courses.home.data.model.courses.Course
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class CoursesAdapter: RecyclerView.Adapter<CoursesAdapter.ViewHolder>() {

    private val items: MutableList<Course> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: CourseItemBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.course_item, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = items[position]
        Picasso.get()
            .load(item.image)
            .into(viewHolder.binding.courseImage, object : Callback {
                override fun onSuccess() {
                    viewHolder.binding.imageProgress.hide()
                }

                override fun onError(e: Exception?) {
                    viewHolder.binding.imageProgress.hide()
                }
            })

        viewHolder.binding.courseName.text = item.title
        viewHolder.binding.courseSessions.text = "${item.numOfSessions} Sessions"

        viewHolder.binding.itemLayout.setOnClickListener {
//            onItemClickListener?.setOnItemClickListener(item)
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<Course>?) {
        clear()
        newItems?.let {
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
            fun setOnItemClickListener(item: String)
    }

    class ViewHolder(val binding: CourseItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}