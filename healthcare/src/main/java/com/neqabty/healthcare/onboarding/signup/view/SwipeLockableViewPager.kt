package com.neqabty.healthcare.onboarding.signup.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


class SwipeLockableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var swipeEnabled = false
    private var animFactor = 0
    private val animator = ValueAnimator()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (swipeEnabled) {
            true -> super.onTouchEvent(event)
            false -> false
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return when (swipeEnabled) {
            true -> super.onInterceptTouchEvent(event)
            false -> false
        }
    }

    fun setSwipePagingEnabled(swipeEnabled: Boolean) {
        this.swipeEnabled = swipeEnabled
    }

    fun animateViewPager(pager: ViewPager, offset: Int, delay: Int) {
        if (!animator.isRunning) {
            animator.removeAllUpdateListeners()
            animator.removeAllListeners()
            //Set animation
            animator.setIntValues(0, -offset)
            animator.duration = delay.toLong()
            animator.repeatCount = 1
            animator.repeatMode = ValueAnimator.RESTART
            animator.addUpdateListener { animation ->
                val value = animFactor * animation.animatedValue as Int
                if (!pager.isFakeDragging) {
                    pager.beginFakeDrag()
                }
                pager.fakeDragBy(value.toFloat())
            }
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    animFactor = 1
                }

                override fun onAnimationEnd(animation: Animator?) {
                    pager.endFakeDrag()
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    animFactor = -1
                }
            })
            animator.start()
        }
    }
}