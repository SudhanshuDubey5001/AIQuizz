package com.sudhanshu.aiquiz.core.utils

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.sudhanshu.aiquiz.R
import java.io.File
import java.io.FileOutputStream
import kotlin.math.max

object Utils {
    fun log(s: String) {
        Log.d("myLog", s)
    }

    val fontFamily = FontFamily(
        Font(
            R.font.comfortaa, FontWeight.Normal
        )
    )
//    fun extractJson(inputString: String): String {
//        // Find the starting and ending index of the JSON object
//        val startIndex = inputString.indexOf("{")
//        val endIndex = inputString.lastIndexOf("}")
//
//        return if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
//            // Extract the JSON substring
//            inputString.substring(startIndex, endIndex + 1)
//        } else {
//            // JSON object not found
//            // TODO: Do it again, 3 times then show error page
//            inputString
//        }
//    }

    fun extractJson(input: String): String? {

        // Remove markdown wrappers
        var cleaned = input
            .replace("```json", "", ignoreCase = true)
            .replace("```", "")
            .trim()

        val start = cleaned.indexOf('{')
        val end = cleaned.lastIndexOf('}')

        return if (start != -1 && end != -1 && end > start) {
            cleaned.substring(start, end + 1)
        } else {
            null
        }
    }

    fun extractJson2(input: String): String? {

        var cleaned = input
            .replace("```json", "", ignoreCase = true)
            .replace("```", "")
            .trim()

        val startObj = cleaned.indexOf('{')
        val startArr = cleaned.indexOf('[')

        val start = listOf(startObj, startArr)
            .filter { it != -1 }
            .minOrNull() ?: return null

        val endObj = cleaned.lastIndexOf('}')
        val endArr = cleaned.lastIndexOf(']')

        val end = maxOf(endObj, endArr)

        if (end < start) return null

        var json = cleaned.substring(start, end + 1).trim()

        // If AI returned an array, wrap it
        if (json.startsWith("[")) {
            json = """{ "questions": $json }"""
        }

        return json
    }

    private fun normalize(text: String): String {
        return text
            .lowercase()
            .replace(Regex("[^a-z0-9 ]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private fun jaccardSimilarity(a: String, b: String): Double {
        val setA = a.lowercase().split(" ").toSet()
        val setB = b.lowercase().split(" ").toSet()

        val intersection = setA.intersect(setB).size
        val union = setA.union(setB).size

        return intersection.toDouble() / union
    }

    private fun similarity(a: String, b: String): Double {
        val longer = if (a.length > b.length) a else b
        val shorter = if (a.length > b.length) b else a

        val distance = levenshteinDistance(longer, shorter)
        return (longer.length - distance) / longer.length.toDouble()
    }

    private fun levenshteinDistance(a: String, b: String): Int {
        val dp = Array(a.length + 1) { IntArray(b.length + 1) }

        for (i in 0..a.length) dp[i][0] = i
        for (j in 0..b.length) dp[0][j] = j

        for (i in 1..a.length) {
            for (j in 1..b.length) {
                val cost = if (a[i - 1] == b[j - 1]) 0 else 1

                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,     // deletion
                    dp[i][j - 1] + 1,     // insertion
                    dp[i - 1][j - 1] + cost // substitution
                )
            }
        }

        return dp[a.length][b.length]
    }

    fun similarityScore(a:String, b:String): Double{
        val lev = similarity(normalize(a), normalize(b))
        val jac = jaccardSimilarity(normalize(a), normalize(b))

        return max(lev, jac)
    }
}