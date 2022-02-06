package com.lobo.myjourney.common.utils

import android.content.Context


class ContextStringResourceProvider (
    private val context: Context,
) : StringResourceProvider {

    override fun getString(resId: Int) = context.getString(resId)

    override fun getString(
        resId: Int,
        vararg formatArgs: Any?,
    ) = context.getString(resId, *formatArgs)

    override fun getQuantityString(resId: Int, quantity: Int, vararg formatArgs: Any?): String {
        return context.resources.getQuantityString(
            resId,
            quantity,
            *formatArgs
        )
    }

    override fun getStringArray(resId: Int): Array<String> = context.resources.getStringArray(resId)
}