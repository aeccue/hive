/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.model

import androidx.room.Entity

@Entity(
    tableName = "LedSectionConfigurations",
    primaryKeys = ["id", "setupId", "group", "name"]
)
data class LedSectionConfiguration(
    val id: Int,
    val setupId: Int,
    val group: LedGroup,
    val name: String,
    val leds: ByteArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LedSectionConfiguration

        if (id != other.id) return false
        if (setupId != other.setupId) return false
        if (group != other.group) return false
        if (name != other.name) return false
        if (!leds.contentEquals(other.leds)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + setupId
        result = 31 * result + group.ordinal
        result = 31 * result + name.hashCode()
        result = 31 * result + leds.contentHashCode()
        return result
    }
}
