/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.app

import aeccue.foundation.dagger.injector.DaggerInjected
import android.app.Service

abstract class InjectedService : Service(), DaggerInjected {

    override fun onCreate() {
        inject()
        super.onCreate()
    }
}
