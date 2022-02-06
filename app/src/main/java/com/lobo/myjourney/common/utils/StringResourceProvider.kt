package com.lobo.myjourney.common.utils

interface StringResourceProvider {
    fun getString(resId: Int): String
    fun getString(
        resId: Int,
        vararg formatArgs: Any?,
    ): String

    fun getQuantityString(
        resId: Int,
        quantity: Int,
        vararg formatArgs: Any? = emptyArray(),
    ): String

    fun getStringArray(resId: Int): Array<String>
}