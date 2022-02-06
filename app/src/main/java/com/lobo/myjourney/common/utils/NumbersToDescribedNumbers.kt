package com.lobo.myjourney.common.utils

import java.text.DecimalFormat

const val MASK = "000000000000"
const val ZERO = "zero"
const val HUNDRED = " hundred"
const val MILLION = " million "
const val THOUSAND = " thousand "
const val BILLION = " billion "

object NumbersToDescribedNumbers {

    private val tensNames = arrayOf(
        "", " ten", " twenty", " thirty", " forty",
        " fifty", " sixty", " seventy", " eighty", " ninety"
    )
    private val numNames = arrayOf(
        "", " one", " two", " three", " four", " five",
        " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen",
        " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"
    )

    private fun Int.convertLessThanOneThousand(): String {
        var number = this
        var soFar: String
        if (number % 100 < 20) {
            soFar = numNames[number % 100]
            number /= 100
        } else {
            soFar = numNames[number % 10]
            number /= 10
            soFar = tensNames[number % 10] + soFar
            number /= 10
        }
        return if (number == 0) soFar else numNames[number] + HUNDRED + soFar
    }

    fun Int.convertNumberToWord(): String {
        if (this == 0) return ZERO

        val number: String = DecimalFormat(MASK).format(this)

        val billions = number.substring(0, 3).toInt()
        val millions = number.substring(3, 6).toInt()
        val hundredThousands = number.substring(6, 9).toInt()
        val thousands = number.substring(9, 12).toInt()

        var result = convertBillions(billions)

        result = convertMillions(millions, result)

        result = convertThousands(hundredThousands, result)

        val tradThousand: String = thousands.convertLessThanOneThousand()
        result += tradThousand

        return result.replace("^\\s+".toRegex(), "").replace("\\b\\s{2,}\\b".toRegex(), " ")
    }

    private fun convertThousands(hundredThousands: Int, result: String): String {
        var number = result
        val tradHundredThousands: String = when (hundredThousands) {
            0 -> ""
            1 -> "one thousand "
            else -> hundredThousands.convertLessThanOneThousand() + THOUSAND
        }
        number += tradHundredThousands
        return number
    }

    private fun convertMillions(millions: Int, result: String): String {
        var number = result
        val tradMillions: String = when (millions) {
            0 -> ""
            1 -> millions.convertLessThanOneThousand() + MILLION
            else -> millions.convertLessThanOneThousand() + MILLION
        }
        number += tradMillions
        return number
    }

    private fun convertBillions(billions: Int): String {
        val tradBillions: String = when (billions) {
            0 -> ""
            1 -> billions.convertLessThanOneThousand() + BILLION
            else -> billions.convertLessThanOneThousand() + BILLION
        }
        return tradBillions
    }
}