package com.lobo.myjourney.common.utils.databinding

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.lobo.myjourney.common.utils.extensions.safeLet
import com.lobo.myjourney.common.utils.extensions.present
import com.lobo.myjourney.common.utils.extensions.visible

@BindingAdapter("present")
fun View.setPresent(value: Boolean?) {
    value?.let {
        visibility = if (it) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("srcCompat")
fun ImageView.setImageCompat(image: Drawable?) {
    setImageDrawable(image)
}

@BindingAdapter("visible")
fun View.setVisible(value: Boolean) {
    visible = value
}

@BindingAdapter(value = ["present", "enterAnimation", "exitAnimation"])
fun View.setPresent(
    value: Boolean?,
    enterAnimation: Int? = null,
    exitAnimation: Int? = null,
) {
    safeLet(value, enterAnimation, exitAnimation) { isPresent, enterAnim, extAnim ->
        val animation = getEnterOrExitAnimation(isPresent, enterAnim, extAnim)
        startAnimation(animation)
    } ?: value?.let { present = it }
}

private fun View.getEnterOrExitAnimation(
    isPresent: Boolean,
    animEnter: Int,
    animEnd: Int,
): Animation? = if (isPresent) {
    AnimationUtils.loadAnimation(context, animEnter).apply {
        setAnimationListener(
            object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation?) {}

                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {
                    present = true
                }
            }
        )
    }
} else {
    AnimationUtils.loadAnimation(context, animEnd).apply {
        setAnimationListener(
            object : Animation.AnimationListener {
                override fun onAnimationEnd(animation: Animation?) {
                    present = false
                }

                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
            }
        )
    }
}

@BindingAdapter("android:background")
fun View.setViewColor(color: Int) {
    if (color != 0) setBackgroundColor(ContextCompat.getColor(context, color))
    else setBackgroundColor(Color.TRANSPARENT)
}

@BindingAdapter("backgroundDrawable")
fun View.setBackgroundDrawable(drawable: Int) {
    if (drawable != 0) background = ContextCompat.getDrawable(context, drawable)
    else setBackgroundColor(Color.TRANSPARENT)
}

@BindingAdapter("bitmap")
fun ImageView.setBitmap(bitmap: Bitmap?) {
    bitmap?.let {
        setImageBitmap(it)
    }
}

@BindingAdapter("data")
fun <T> RecyclerView.setRecyclerViewProperties(
    previousData: T,
    data: T,
) {
    if (adapter is BindableAdapter<*> && previousData != data) {
        (adapter as BindableAdapter<T>).setData(data)
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("items")
fun <T> setItems(
    view: RecyclerView,
    previousItems: List<T>?,
    newItems: List<T>?,
) {
    if (previousItems != newItems) {
        val adapter = view.adapter as ListAdapter<T, RecyclerView.ViewHolder>
        adapter.submitList(newItems)
    }
}

@BindingAdapter("hideIfNull")
fun View.hideIfNull(value: Any?) {
    this.present = value != null
}

@BindingAdapter("hideIfNullOrBlank")
fun View.hideIfNullOrBlank(value: String?) {
    this.present = !value.isNullOrBlank()
}

@BindingAdapter("invisibleIfNull")
fun View.invisibleIfNull(value: Any?) {
    this.visible = value != null
}

@BindingAdapter("textOrGone")
fun TextView.textOrGone(text: String?) {
    this.text = text
    this.present = !text.isNullOrBlank()
}

@BindingAdapter("android:src")
fun ImageView.setSrcBind(@DrawableRes drawable: Int) {
    setImageResource(drawable)
}

@BindingAdapter("android:text")
fun TextView.setTextBind(@StringRes string: Int) {
    if (string != 0) text = context.getString(string)
}

@BindingAdapter("android:textColor")
fun TextView.setTextColorBind(@ColorRes color: Int) {
    if (color != 0) setTextColor(ContextCompat.getColor(context, color))
}

@BindingAdapter("colorBind")
fun ImageView.setColorBind(@ColorRes color: Int) {
    if (color != 0) setColorFilter(ContextCompat.getColor(context, color))
}

@BindingAdapter("strikeThrough")
fun TextView.strikeThrough(
    strikeThrough: Boolean,
) {
    paintFlags = if (strikeThrough) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

@BindingAdapter("inputError")
fun TextInputLayout.setTextInputLayoutErrorMessage(
    previousValue: Int?,
    newValue: Int?,
) {
    if (newValue == null) {
        isErrorEnabled = false
    } else {
        if (newValue != previousValue) error = context.getString(newValue)
    }
}

@BindingAdapter("html")
fun TextView.setHtmlText(html: String?) {
    this.text = html?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(it)
        }
    }
}


@BindingAdapter("toolbarText")
fun TextView.setViewText(string: String?) {
    if (string != null) text = string
}