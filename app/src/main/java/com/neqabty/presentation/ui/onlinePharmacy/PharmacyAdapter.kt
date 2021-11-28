package com.neqabty.presentation.ui.onlinePharmacy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.R
import com.neqabty.databinding.PharmacyItemBinding
import com.neqabty.yodawy.modules.CartAdapter


class PharmacyAdapter(private val context: Context) : RecyclerView.Adapter<PharmacyAdapter.ListViewHolder>() {

    private var pharmaciesList: MutableList<Pharmacy> = ArrayList()

    private val originalWidth = context.screenWidth - 48.dp
    private val expandedWidth = context.screenWidth - 24.dp
    private var originalHeight = -1 // will be calculated dynamically
    private var expandedHeight = -1 // will be calculated dynamically
    var onItemClickListener: OnItemClickListener? = null
    private val listItemExpandDuration: Long get() = (300L / animationPlaybackSpeed).toLong()

    private lateinit var recyclerView: RecyclerView
    private var expandedModel: Pharmacy? = null

    override fun getItemCount(): Int = pharmaciesList.size
    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val binding: PharmacyItemBinding =
            DataBindingUtil.inflate(layoutInflater!!,
                R.layout.pharmacy_item, parent, false)

        return ListViewHolder(binding)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.binding.rootLayout.animation =
            AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

        val model = pharmaciesList[position]
        holder.binding.pharmacyImage.setImageResource(model.image)
        holder.binding.serviceDetails.text = model.title
        holder.binding.serviceBody.text = model.text
        expandItem(holder, model == expandedModel, animate = false)
        scaleDownItem(holder, position, false)
        holder.binding.getService.setOnClickListener {
            onItemClickListener?.setOnItemClickListener(position)
        }
        holder.binding.cardContainer.setOnClickListener {
            if (expandedModel == null) {

                // expand clicked view
                expandItem(holder, expand = true, animate = true)

                expandedModel = model
            } else if (expandedModel == model) {

                // collapse clicked view
                expandItem(holder, expand = false, animate = true)

                expandedModel = null
            } else {

                // collapse previously expanded view
                val expandedModelPosition = pharmaciesList.indexOf(expandedModel!!)
                val oldViewHolder =
                    recyclerView.findViewHolderForAdapterPosition(expandedModelPosition) as? ListViewHolder
                if (oldViewHolder != null) expandItem(oldViewHolder, expand = false, animate = true)

                // expand clicked view
                expandItem(holder, expand = true, animate = true)
                expandedModel = model
            }
        }
    }

    fun submitList(orders: List<Pharmacy>?) {
        orders?.let {
            pharmaciesList = it.toMutableList()
            notifyDataSetChanged()
        }
    }

    interface OnItemClickListener {
        fun setOnItemClickListener(itemId: Int)
    }

    private fun expandItem(holder: ListViewHolder, expand: Boolean, animate: Boolean) {
        if (animate) {
            val animator = getValueAnimator(
                expand, listItemExpandDuration, AccelerateDecelerateInterpolator()
            ) { progress -> setExpandProgress(holder, progress) }

            if (expand) animator.doOnStart {
                holder.binding.expandView.isVisible = true
            }
            else animator.doOnEnd {
                holder.binding.expandView.isVisible = false
            }

            animator.start()
        } else {

            // show expandView only if we have expandedHeight (onViewAttached)
            holder.binding.expandView.isVisible = expand && expandedHeight >= 0
            setExpandProgress(holder, if (expand) 1f else 0f)
        }
    }

    override fun onViewAttachedToWindow(holder: ListViewHolder) {
        super.onViewAttachedToWindow(holder)

        // get originalHeight & expandedHeight if not gotten before
        if (expandedHeight < 0) {
            expandedHeight = 0 // so that this block is only called once

            holder.binding.cardContainer.doOnLayout { view ->
                originalHeight = view.height

                // show expandView and record expandedHeight in next layout pass
                // (doOnPreDraw) and hide it immediately. We use onPreDraw because
                // it's called after layout is done. doOnNextLayout is called during
                // layout phase which causes issues with hiding expandView.
                holder.binding.expandView.isVisible = true

                view.doOnPreDraw {
                    expandedHeight = view.height
                    holder.binding.expandView.isVisible = false
                }
            }
        }
    }

    private fun setExpandProgress(holder: ListViewHolder, progress: Float) {
        if (expandedHeight > 0 && originalHeight > 0) {
            holder.binding.cardContainer.layoutParams.height =
                (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
        }
        holder.binding.cardContainer.layoutParams.width =
            (originalWidth + (expandedWidth - originalWidth) * progress).toInt()

        holder.binding.cardContainer.requestLayout()

        holder.binding.arrow.rotation = 180 * progress
    }

    ///////////////////////////////////////////////////////////////////////////
    // Scale Down Animation
    ///////////////////////////////////////////////////////////////////////////

    private fun setScaleDownProgress(holder: ListViewHolder, position: Int, progress: Float) {
        val itemExpanded = position >= 0 && pharmaciesList[position] == expandedModel
        holder.binding.cardContainer.layoutParams.apply {
            width = ((if (itemExpanded) expandedWidth else originalWidth) * (1 - 0.1f * progress)).toInt()
            height = ((if (itemExpanded) expandedHeight else originalHeight) * (1 - 0.1f * progress)).toInt()
//            log("width=$width, height=$height [${"%.2f".format(progress)}]")
        }
        holder.binding.cardContainer.requestLayout()

        holder.binding.scaleContainer.scaleX = 1 - 0.05f * progress
        holder.binding.scaleContainer.scaleY = 1 - 0.05f * progress


        holder.binding.listItemFg.alpha = progress
    }

    /** Convenience method for calling from onBindViewHolder */
    private fun scaleDownItem(holder: ListViewHolder, position: Int, isScaleDown: Boolean) {
        setScaleDownProgress(holder, position, if (isScaleDown) 1f else 0f)
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewHolder
    ///////////////////////////////////////////////////////////////////////////



    class ListViewHolder(val binding: PharmacyItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}