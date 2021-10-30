package ru.hse.smartUniversity

fun findRoute(@Suppress("UNUSED_PARAMETER") map:    UniversityMap,
              @Suppress("UNUSED_PARAMETER") start:  String,
              @Suppress("UNUSED_PARAMETER") finish: String): Array<Int>? {
    return if (start == finish) emptyArray() else arrayOf(finish.toInt())
}
