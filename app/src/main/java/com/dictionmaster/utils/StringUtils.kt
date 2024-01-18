package com.dictionmaster.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

object StringUtils {

    @JvmStatic
    fun getStyledText(position: String, partOfSpeech: String, definition: String): SpannableString {
        val styledText = SpannableString("$position $partOfSpeech $definition")

        // Convert hex color to an integer representation
        val parsedColor = Color.parseColor("#80052D39")


        // Apply style to partOfSpeech (text color)
        val partOfSpeechColor = ForegroundColorSpan(parsedColor)
        styledText.setSpan(
            partOfSpeechColor,
            position.length,
            "$position $partOfSpeech".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return styledText
    }

    @JvmStatic
    fun formatExample(example: String?): CharSequence {
        val formattedExample = example?.replace("\\s{2,}".toRegex(), "\n\u2022 ") ?: ""
        return "• $formattedExample"
    }

}
