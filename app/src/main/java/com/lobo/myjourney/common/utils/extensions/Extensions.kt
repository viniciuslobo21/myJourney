package com.lobo.myjourney.common.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Base64
import android.util.Property
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.webkit.WebView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.lobo.myjourney.R
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.util.Stack

inline fun <T> guard(
    p: T?,
    block: () -> Unit
) {
    if (p == null) block()
}

inline fun guard(
    condition: Boolean,
    block: () -> Unit
) {
    if (!condition) block()
}

inline fun <R> ifOrNull(
    condition: Boolean,
    block: () -> R
): R? = if (condition) block() else null

inline fun <T1, T2, R> safeLet(
    p1: T1?,
    p2: T2?,
    block: (T1, T2) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            block(safeP1, safeP2)
        }
    }
}

inline fun <T1, T2, T3, R> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            p3?.let { safeP3 ->
                block(safeP1, safeP2, safeP3)
            }
        }
    }
}

inline fun <T1, T2, T3, T4, R> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            p3?.let { safeP3 ->
                p4?.let { safeP4 ->
                    block(safeP1, safeP2, safeP3, safeP4)
                }
            }
        }
    }
}

inline fun <T1, T2, T3, T4, T5, R> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            p3?.let { safeP3 ->
                p4?.let { safeP4 ->
                    p5?.let { safeP5 ->
                        block(safeP1, safeP2, safeP3, safeP4, safeP5)
                    }
                }
            }
        }
    }
}

inline fun <T1, T2, T3, T4, T5, T6, R> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    p6: T6?,
    block: (T1, T2, T3, T4, T5, T6) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            p3?.let { safeP3 ->
                p4?.let { safeP4 ->
                    p5?.let { safeP5 ->
                        p6?.let { safeP6 ->
                            block(safeP1, safeP2, safeP3, safeP4, safeP5, safeP6)
                        }
                    }
                }
            }
        }
    }
}

inline fun <T1, T2, T3, T4, T5, T6, T7, R> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    p6: T6?,
    p7: T7?,
    block: (T1, T2, T3, T4, T5, T6, T7) -> R
): R? {
    return p1?.let { safeP1 ->
        p2?.let { safeP2 ->
            p3?.let { safeP3 ->
                p4?.let { safeP4 ->
                    p5?.let { safeP5 ->
                        p6?.let { safeP6 ->
                            p7?.let { safeP7 ->
                                block(safeP1, safeP2, safeP3, safeP4, safeP5, safeP6, safeP7)
                            }
                        }
                    }
                }
            }
        }
    }
}

inline fun <T> Iterable<T>.sumByBigDecimal(selector: (T) -> BigDecimal): BigDecimal {
    var sum: BigDecimal = BigDecimal.ZERO
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun <T> Collection<T>.asPairs(): Collection<Pair<T, T?>> {
    return withIndex().groupBy { it.index / 2 }.entries.mapNotNull {
        when (it.value.size) {
            1 -> Pair(it.value[0].value, null)
            2 -> Pair(it.value[0].value, it.value[1].value)
            else -> null
        }
    }
}

fun <K, V> MutableMap<K, V>.putIfNotNull(
    key: K,
    value: V?
) {
    if (value != null) {
        put(key, value)
    }
}

fun <T> MutableList<T>.addIf(
    item: T,
    predicate: () -> Boolean
) {
    if (predicate.invoke()) add(item)
}

fun <T> Stack<T>.peekOrNull(): T? = this.takeIf { it.isNotEmpty() }?.peek()

fun View.hide() {
    this.visibility = View.GONE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.showIf(condition: () -> Boolean) {
    if (condition()) show()
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.enterPopUp() {
    val animation = AnimationUtils.loadAnimation(this.context, R.anim.enter_popup_anim)
    animation.setAnimationListener(
        object : Animation.AnimationListener {
            override fun onAnimationEnd(p0: Animation?) {}

            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationStart(p0: Animation?) {
                visibility = View.VISIBLE
            }
        }
    )
    startAnimation(animation)
}

fun View.exitPopUp() {
    val animation = AnimationUtils.loadAnimation(this.context, R.anim.exit_popup_anim)
    animation.setAnimationListener(
        object : Animation.AnimationListener {
            override fun onAnimationEnd(p0: Animation?) {
                visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationStart(p0: Animation?) {}
        }
    )
    startAnimation(animation)
}

fun View.visibleAnimate(
    startDelay: Long = 0,
    duration: Long = 300,
    callback: ((v: View?) -> Unit)? = null
) {
    animate().apply {
        this.startDelay = startDelay
    }.alpha(1f).setDuration(duration).setListener(
        object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                visibility = View.VISIBLE
                callback?.invoke(this@visibleAnimate)
            }
        }
    )
}

fun View.invisibleAnimate(
    startDelay: Long = 0,
    duration: Long = 300,
    callback: ((v: View?) -> Unit)? = null
) {
    hideAnimate(View.INVISIBLE, startDelay, duration, callback)
}

fun View.goneAnimate(
    startDelay: Long = 0,
    duration: Long = 300,
    callback: ((v: View?) -> Unit)? = null
) {
    hideAnimate(View.GONE, startDelay, duration, callback)
}

fun Drawable.animate() {
    (this as? AnimatedVectorDrawable)?.start()
}

private fun View.hideAnimate(
    hidingStrategy: Int,
    startDelay: Long = 0,
    duration: Long = 300,
    callback: ((v: View?) -> Unit)? = null
) {
    animate().apply {
        this.startDelay = startDelay
    }.alpha(0f).setDuration(duration).setListener(
        object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                visibility = hidingStrategy
                callback?.invoke(this@hideAnimate)
            }
        }
    )
}

fun View.setVisibleOrGone(predicate: Boolean) {
    this.visibility = if (predicate) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.setVisibleOrInvisible(predicate: Boolean) {
    this.visibility = if (predicate) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

fun TextView.animateTextColor(
    @ColorRes color: Int,
    startDelay: Long = 0,
    duration: Long = 1000
) {

    val property = object : Property<TextView, Int>(Int::class.javaPrimitiveType, "textColor") {
        override fun get(`object`: TextView?): Int? = `object`?.currentTextColor

        override fun set(
            `object`: TextView?,
            value: Int?
        ) {
            value?.let { `object`?.setTextColor(it) }
        }
    }
    ObjectAnimator.ofInt(this, property, ContextCompat.getColor(context, color)).apply {
        this.duration = duration
        this.startDelay = startDelay
        setEvaluator(ArgbEvaluator())
        interpolator = DecelerateInterpolator(2f)
    }.start()
}

fun TextView.updateWithStyledAttributes(
    styledAttributes: TypedArray,
    styleableRes: Int,
    styleableFont: Int,
    defaultTextFont: Int,
    defaultTextSize: Int
) {

    val styledTextSize = styledAttributes.getDimensionPixelSize(styleableRes, defaultTextSize).toFloat()
    this.typeface = styledAttributes.getTypeFace(context, defaultTextFont, styleableFont)
    setTextSize(TypedValue.COMPLEX_UNIT_SP, pxToSp(styledTextSize))
}

fun View.applyWindowInsetsForToolbar(toolbar: View) {
    ViewCompat.setOnApplyWindowInsetsListener(toolbar) { v, insets ->
        (v.layoutParams as ViewGroup.MarginLayoutParams).topMargin = insets.systemWindowInsetTop
        insets.consumeSystemWindowInsets()
    }
}

fun View.setPaddingTop(padding: Int) {
    this.setPadding(this.paddingLeft, padding, this.paddingRight, this.paddingBottom)
}

fun View.addPaddingTop(padding: Int) {
    this.setPadding(this.paddingLeft, this.paddingTop + padding, this.paddingRight, this.paddingBottom)
}

fun View.setMarginBottom(margin: Int) {
    (this.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        setMargins(leftMargin, topMargin, rightMargin, margin)
    }
}

fun View.pxToDp(pxSize: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxSize, resources.displayMetrics)

fun View.dpToPixels(dpSize: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, resources.displayMetrics)

fun View.pxToSp(pxSize: Float): Float = pxSize / resources.displayMetrics.scaledDensity

fun View.dpToSp(dpSize: Float): Float = dpToPixels(dpSize) / resources.displayMetrics.scaledDensity

fun View.setOnDebounceClickListener(
    debounceTime: Long,
    callback: (v: View?) -> Unit
) {
    this.setOnClickListener(DebouncedClickListener(debounceTime, callback))
}

fun View.measureHeight(widthSpec: Int = View.MeasureSpec.AT_MOST): Int {
    val deviceWidth: Int = Point().let {
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(it)
        it.x
    }
    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, widthSpec)
    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(widthMeasureSpec, heightMeasureSpec)
    return measuredHeight
}

fun View.measureHeightIfVisible() = if (isVisible()) {
    measure(0, 0)
    measuredHeight
} else {
    0
}

fun Context.deviceHeightInPixels(): Int = resources.displayMetrics.heightPixels

class DebouncedClickListener(
    val debounceTime: Long,
    val onDebouncedClick: (v: View?) -> Unit
) : View.OnClickListener {
    val lastClickMap = mutableMapOf<View?, Long>()

    override fun onClick(v: View?) {
        val lastPress = lastClickMap[v]
        val now = SystemClock.uptimeMillis()

        lastClickMap[v] = now
        if (lastPress == null || Math.abs(now - lastPress) > debounceTime) {
            onDebouncedClick(v)
        }
    }
}

fun View.setWidth(width: Int) {
    this.layoutParams = this.layoutParams.apply {
        this.width = width
    }
}

fun View.setHeight(height: Int) {
    this.layoutParams = this.layoutParams.apply {
        this.height = height
    }
}

fun View.setMargins(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.apply {
        left?.let { leftMargin = left }
        top?.let { topMargin = top }
        right?.let { rightMargin = right }
        bottom?.let { bottomMargin = bottom }
    }
    layoutParams = params
}

fun View.addMargins(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(
        params.leftMargin + left,
        params.topMargin + top,
        params.rightMargin + right,
        params.bottomMargin + bottom
    )
    layoutParams = params
}

val RecyclerView.ViewHolder.resources: Resources
    get() = itemView.resources

fun RecyclerView?.getLinearLayoutManager(): LinearLayoutManager? = this?.layoutManager as? LinearLayoutManager

fun FragmentTransaction.addSlideTransitionAnimation(): FragmentTransaction {
    return this.setCustomAnimations(
        R.anim.enter_from_right,
        R.anim.exit_to_left,
        R.anim.enter_from_left,
        R.anim.exit_to_right
    )
}

fun FragmentTransaction.addFadeTransitionAnimation(): FragmentTransaction =
    this.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)

fun FragmentTransaction.addSlideFromBottomAnimation(): FragmentTransaction =
    this.setCustomAnimations(R.anim.enter_from_bottom, R.anim.none, R.anim.none, R.anim.exit_from_top)

fun <T : Fragment> FragmentTransaction.replaceFragmentWithSlideTransition(
    containerId: Int,
    fragment: Class<T>,
    args: Bundle?,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null
): FragmentTransaction {
    return this.apply {
        addSlideTransitionAnimation()
        replace(containerId, fragment, args, tag)
        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun FragmentTransaction.replaceFragmentWithSlideTransition(
    containerId: Int,
    fragment: Fragment,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null
): FragmentTransaction {
    return this.apply {
        addSlideTransitionAnimation()
        replace(containerId, fragment, tag)
        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun <T : Fragment> FragmentTransaction.replaceFragmentWithFadeTransition(
    containerId: Int,
    fragment: Class<T>,
    args: Bundle?,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null
): FragmentTransaction {
    return this.apply {
        addFadeTransitionAnimation()
        replace(containerId, fragment, args, tag)
        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun FragmentTransaction.replaceFragmentWithFadeTransition(
    containerId: Int,
    fragment: Fragment,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null
): FragmentTransaction {
    return this.apply {
        addFadeTransitionAnimation()
        replace(containerId, fragment, tag)
        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun <T : Fragment> FragmentTransaction.replaceFragmentWithSlideFromBottomTransition(
    containerId: Int,
    fragment: Class<T>,
    args: Bundle?,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null
): FragmentTransaction {
    return this.apply {
        addSlideFromBottomAnimation()
        replace(containerId, fragment, args, tag)
        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun FragmentTransaction.replaceFragmentWithSlideFromBottomTransition(
    containerId: Int,
    fragment: Fragment,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null
): FragmentTransaction {
    return this.apply {
        addSlideFromBottomAnimation()
        replace(containerId, fragment, tag)
        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun <T : Fragment> FragmentTransaction.replaceFragmentWithoutTransition(
    containerId: Int,
    fragment: Class<T>,
    args: Bundle?,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null
): FragmentTransaction {
    return this.apply {
        replace(containerId, fragment, args, tag)
        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun FragmentTransaction.replaceFragmentWithoutTransition(
    containerId: Int,
    fragment: Fragment,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null
): FragmentTransaction {
    return this.apply {
        replace(containerId, fragment, tag)
        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun FragmentTransaction.addFragmentWithoutTransition(
    fragment: Fragment,
    tag: String?,
    addToBackStack: Boolean = true,
    backStackName: String? = null,
    containerId: Int? = null
): FragmentTransaction {
    return this.apply {
        containerId?.let {
            add(it, fragment, tag)
        } ?: add(fragment, tag)

        if (addToBackStack) addToBackStack(backStackName)
    }
}

fun FragmentManager.commitTransactionSafely(
    transaction: FragmentTransaction,
    allowStateLoss: Boolean = false
) {
    if (!this.isStateSaved) {
        transaction.commit()
    } else if (allowStateLoss) {
        transaction.commitAllowingStateLoss()
    } else {
        // Cannot perform this operation. A commit will lead to a IllegalStateException
    }
}

fun FragmentManager.commitTransactionNowSafely(
    transaction: FragmentTransaction,
    allowStateLoss: Boolean = false
) {
    if (!this.isStateSaved) {
        transaction.commitNow()
    } else if (allowStateLoss) {
        transaction.commitNowAllowingStateLoss()
    } else {
        // Cannot perform this operation. A commit will lead to a IllegalStateException
    }
}

fun FragmentManager.popBackStackSafely(
    backStackName: String? = null,
    allowIgnoreOnStateSaved: Boolean = false,
    flags: Int = 0
) {
    if (!isStateSaved || (isStateSaved && !allowIgnoreOnStateSaved)) {
        popBackStack(backStackName, flags)
    }
}

fun TextInputLayout.clearError() {
    this.error = null
    this.isErrorEnabled = false
}

fun TextInputLayout.clearErrorOnly() {
    if (this.error != null) {
        this.error = null
    }
}

fun String.decodeBase64() = String(Base64.decode(this, Base64.DEFAULT), StandardCharsets.UTF_8)

fun CharSequence.boldSpan(text: String): CharSequence {
    return SpannableStringBuilder(this).apply {
        setSpan(
            android.text.style.StyleSpan(Typeface.BOLD),
            this.indexOf(text),
            this.indexOf(text) + text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

fun CharSequence.colorSpan(text: String, @ColorInt color: Int): CharSequence {
    return SpannableStringBuilder(this).apply {
        setSpan(
            ForegroundColorSpan(color),
            indexOf(text),
            indexOf(text) + text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Context.locationManager(): LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

fun Context.connectivityManager(): ConnectivityManager =
    this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.accessibilityManager(): AccessibilityManager =
    this.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

fun Context.activityManager(): ActivityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

fun Context.clipboardManager(): ClipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

fun Context.getTextFromClipboard(): String? {
    return clipboardManager()
        .takeIf {
            it.hasPrimaryClip() && it.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) ?: false
        }?.let { it.primaryClip?.getItemAt(0)?.text?.toString() }
}

fun Context.setTextToClipboard(
    label: String,
    text: String
) {
    val clip: ClipData = ClipData.newPlainText(label, text)
    clipboardManager().setPrimaryClip(clip)
}

fun Context.goToPlayStore() {
    this.startActivity(
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("market://details?id=" + this@goToPlayStore.packageName)
        }
    )
}

private const val MIMETYPE_TEXT = "text/plain"

fun FragmentActivity.shareText(
    text: String,
    dialogTitle: String
) {
    Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, text)
        type = MIMETYPE_TEXT
    }.run {
        startActivity(Intent.createChooser(this, dialogTitle))
    }
}

fun Fragment.getNullableChildFragmentManager(): FragmentManager? {
    return if (isAdded) {
        childFragmentManager
    } else {
        null
    }
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }

fun String.fromHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION") return Html.fromHtml(this)
    }
}

fun Group.setAllOnClickListener(block: (view: View) -> Unit) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id)?.setOnClickListener { view -> block(view) }
    }
}

fun Long?.toCashBigDecimal(): BigDecimal = BigDecimal(this ?: 0L).divide(BigDecimal(100))

fun Int?.toCashBigDecimal(): BigDecimal = BigDecimal(this ?: 0).divide(BigDecimal(100))

fun BigDecimal?.toCash(): BigDecimal = (this ?: BigDecimal(0)).divide(BigDecimal(100))

fun BigDecimal?.toCents(): Int = this?.multiply(BigDecimal(100))?.toInt() ?: 0

fun BigDecimal?.isZero(): Boolean {
    return this == BigDecimal.ZERO
}

fun BigDecimal?.isNullOrZero() = this?.let { it == BigDecimal.ZERO } ?: true

fun Double?.toCash(): Long = this?.let { (it / 100).toLong() } ?: 0L

fun Double?.toCents(): Long = this?.let { (it * 100).toLong() } ?: 0L

typealias StringFunc = (String) -> Unit

typealias Promise = (StringFunc, StringFunc) -> Unit

fun WebView.callJSFunction(
    functionName: String,
    arguments: List<String>,
    callback: StringFunc? = null
) {
    Handler(Looper.getMainLooper()).post {
        evaluateJavascript("javascript:$functionName(${arguments.joinToString(", ")})", callback)
    }
}

fun WebView.promise(
    callbackName: String,
    block: Promise
) {
    var alreadyResolved = false
    val resolve: StringFunc = resolve@{
        if (alreadyResolved) return@resolve
        alreadyResolved = true

        callJSFunction("${callbackName}Success", listOf(it))
    }
    val reject: StringFunc = reject@{
        if (alreadyResolved) return@reject
        alreadyResolved = true

        callJSFunction("${callbackName}Error", listOf(it))
    }

    block(resolve, reject)
}

fun Double.roundCoordinate(): Double = BigDecimal(this).setScale(3, BigDecimal.ROUND_HALF_EVEN).toDouble()

fun TypedArray.getTypeFace(
    context: Context,
    fontDefaultId: Int,
    styleableRes: Int
): Typeface? {
    val newFontId = if (hasValue(styleableRes)) {
        getResourceId(styleableRes, fontDefaultId)
    } else {
        fontDefaultId
    }

    return ResourcesCompat.getFont(context, newFontId)
}

inline fun <T> runCatchingOrNull(block: () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}

fun View.setHeadingForAccessibility(isHeading: Boolean) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        isAccessibilityHeading = isHeading
    } else {
        val accessibilityDelegate = object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                info.isHeading = isHeading
                super.onInitializeAccessibilityNodeInfo(host, info)
            }
        }
        ViewCompat.setAccessibilityDelegate(this, accessibilityDelegate)
    }