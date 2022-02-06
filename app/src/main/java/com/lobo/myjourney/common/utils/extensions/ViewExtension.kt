package com.lobo.myjourney.common.utils.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val KEYBOARD_LAYOUT_DIFF = 200

fun View.listenToKeyboard(onKeyboardStateChange: (visible: Boolean) -> Unit) {
    var isKeyboardOpened = false
    viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        rootView.getWindowVisibleDisplayFrame(rect)
        val heightDiffInPx = rootView.rootView.height - (rect.bottom - rect.top)
        val heightDiffInDp = heightDiffInPx / resources.displayMetrics.density
        val isVisible = heightDiffInDp > KEYBOARD_LAYOUT_DIFF
        if (isKeyboardOpened != isVisible) {
            isKeyboardOpened = isVisible
            onKeyboardStateChange(isKeyboardOpened)
        }
    }
}

var View.visible: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else INVISIBLE
    }

var View.present: Boolean
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

fun ImageView.setTint(@ColorRes colorRes: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ContextCompat.getColorStateList(context, colorRes)
    )
}

fun View.animateCollapse(duration: Long = 1000): Animation {
    val animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) {
            if (interpolatedTime == 1f) {
                hide()
            } else {
                layoutParams.height = measuredHeight - (measuredHeight * interpolatedTime).toInt()
                requestLayout()
            }
        }

        override fun willChangeBounds() = true
    }.apply { this.duration = duration }
    startAnimation(animation)
    return animation
}

fun View.animateExpand(duration: Long = 1000): Animation {
    val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = measuredHeight

    visibility = VISIBLE
    val animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) {
            layoutParams.height =
                if (interpolatedTime == 1f) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    (targetHeight * interpolatedTime).toInt()
                }
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    animation.duration = duration
    startAnimation(animation)
    return animation
}

fun View.convertToBitmap(): Bitmap {
    val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(measureSpec, measureSpec)
    layout(0, 0, measuredWidth, measuredHeight)
    val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(Color.TRANSPARENT)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}

fun View.executeHapticFeedback(
    isLongClick: Boolean = false,
    event: MotionEvent? = null
) {
    val hapticFeedback = if (isLongClick) HapticFeedbackConstants.LONG_PRESS
    else HapticFeedbackConstants.VIRTUAL_KEY

    if (event == null || event.actionMasked == MotionEvent.ACTION_DOWN) {
        isHapticFeedbackEnabled = true
        performHapticFeedback(hapticFeedback)
    }
}

fun RecyclerView.smoothScroolToPosition(position: Int) {
    if (position < 0) return
    post { layoutManager?.smoothScrollToPosition(this, null, position) }
}

const val ITEMS_UNTIL_END_DEFAULT = 1
const val DEBOUNCE_TIME_DEFAULT = 500L
val MINIMUM_SCROLL_OFFSET_DEFAULT = 20.px

fun View.setGridLayoutMarginStart(@DimenRes marginStart: Int) {
    val layoutParams = layoutParams as GridLayoutManager.LayoutParams
    layoutParams.marginStart = context.resources.getDimension(marginStart).toInt()
    this.layoutParams = layoutParams
}

fun View.setGridLayoutMarginEnd(@DimenRes marginEnd: Int) {
    val layoutParams = layoutParams as GridLayoutManager.LayoutParams
    layoutParams.marginEnd = context.resources.getDimension(marginEnd).toInt()
    this.layoutParams = layoutParams
}