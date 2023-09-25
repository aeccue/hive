/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.app

import aeccue.foundation.dagger.injector.DaggerInjected
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity

abstract class InjectedActivity : ComponentActivity(), DaggerInjected {

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }
}
