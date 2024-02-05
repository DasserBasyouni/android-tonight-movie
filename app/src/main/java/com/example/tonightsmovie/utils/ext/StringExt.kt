package com.example.tonightsmovie.utils.ext

import java.util.Locale


fun String.capitalizeFirstLetter(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}