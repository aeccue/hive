/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.module

import aeccue.foundation.android.screen.ScreenContainer

interface ScreenContainerModule {

    fun init(context: ScreenContainer) {}

    fun destroy(context: ScreenContainer) {}
}
