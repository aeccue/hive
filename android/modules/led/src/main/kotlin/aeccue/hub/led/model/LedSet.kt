/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.model

import androidx.room.Embedded
import androidx.room.Relation

data class LedSet(
    @Embedded val setup: LedSetup,

    @Relation(
        parentColumn = "id",
        entityColumn = "setupId"
    )
    val sections: List<LedSection>
)
