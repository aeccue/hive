/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.model

import androidx.room.Entity

const val BYTES_PER_LED = 3

@Entity(
    tableName = "LedSections",
    primaryKeys = ["id", "setupId", "group"]
)
data class LedSection(
    val id: Int,
    val setupId: Int,
    val group: LedGroup,
    val name: String,
    val ledCount: Int,
    val leds: ByteArray = ByteArray(ledCount * BYTES_PER_LED),
    val on: Boolean = false
) {

    fun validate(): Boolean {
        return id >= 0 &&
                setupId >= 0 &&
                name.isNotBlank() &&
                ledCount > 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LedSection

        if (id != other.id) return false
        if (setupId != other.setupId) return false
        if (group != other.group) return false
        if (name != other.name) return false
        if (ledCount != other.ledCount) return false
        if (!leds.contentEquals(other.leds)) return false
        if (on != other.on) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + setupId
        result = 31 * result + group.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + ledCount
        result = 31 * result + leds.contentHashCode()
        result = 31 * result + on.hashCode()
        return result
    }
}
