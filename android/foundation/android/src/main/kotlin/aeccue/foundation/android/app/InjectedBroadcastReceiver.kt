/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.app

import aeccue.foundation.dagger.injector.DaggerInjected
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.concurrent.atomic.AtomicBoolean

abstract class InjectedBroadcastReceiver : BroadcastReceiver(), DaggerInjected {

    private val injected = AtomicBoolean()

    override fun onReceive(context: Context, intent: Intent) {
        if (injected.compareAndSet(false, true)) {
            inject()
        }

        onInjectedReceive(context, intent)
    }

    protected abstract fun onInjectedReceive(context: Context, intent: Intent)
}
