/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.module

import android.app.Application

interface ProcessModule {

    fun init(context: Application)
}
