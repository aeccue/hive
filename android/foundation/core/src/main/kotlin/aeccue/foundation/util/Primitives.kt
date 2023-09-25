/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.util


val Int.Companion.INVALID_VALUE get() = MIN_VALUE
val Long.Companion.INVALID_VALUE get() = MIN_VALUE
val Float.Companion.INVALID_VALUE get() = NaN

fun Int.cantorPair(with: Int): Long {
    val k1 = this.toLong()
    val k2 = with.toLong()
    if (this < 0 || k2 < 0) throw IllegalArgumentException("Cantor pairing function only works with positive numbers")
    return (((k1 + k2) * (k1 + k2 + 1)) / 2) + k2
}
