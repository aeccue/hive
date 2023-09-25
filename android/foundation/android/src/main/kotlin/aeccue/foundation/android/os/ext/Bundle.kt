package aeccue.foundation.android.os.ext

import aeccue.foundation.util.INVALID_VALUE
import android.os.Bundle

fun Bundle.getOptionalInt(key: String): Int? {
    val value = getInt(key, Int.INVALID_VALUE)
    return if (value == Int.INVALID_VALUE) null else value
}

fun Bundle.getOptionalLong(key: String): Long? {
    val value = getLong(key, Long.INVALID_VALUE)
    return if (value == Long.INVALID_VALUE) null else value
}

fun Bundle.getOptionalFloat(key: String): Float? {
    val value = getFloat(key, Float.INVALID_VALUE)
    return if (value == Float.INVALID_VALUE) null else value
}
