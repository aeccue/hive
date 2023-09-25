/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.app

import aeccue.foundation.android.app.BaseApplication
import aeccue.hub.dagger.DaggerHubDaggerComponent

class HubApplication : BaseApplication() {

    override fun inject() {
        DaggerHubDaggerComponent.factory().create(this).init(this)
    }
}
