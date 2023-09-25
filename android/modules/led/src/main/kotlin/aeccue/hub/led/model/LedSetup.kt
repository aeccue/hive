/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.led.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity

@Entity(
    tableName = "LedSetups",
    primaryKeys = ["id", "group"]
)
data class LedSetup(
    val group: LedGroup,
    val id: Int,
    val name: String,
    val icon: LedSetupIcon,
    val on: Boolean = false
) {

    fun validate(): Boolean {
        return id >= 0 &&
                name.isNotBlank()
    }

}

enum class LedSetupIcon(val vector: ImageVector) {
    Closet(Icons.Outlined.Window),
    DeskLamp(Icons.Outlined.Light),
    DeskShelf(Icons.Outlined.ViewAgenda),
    Monitor(Icons.Outlined.DesktopWindows),
    Stairs(Icons.Outlined.Stairs);
}
