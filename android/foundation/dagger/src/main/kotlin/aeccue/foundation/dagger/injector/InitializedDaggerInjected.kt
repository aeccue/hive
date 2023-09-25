/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

import aeccue.foundation.util.Initializable

interface InitializedDaggerInjected : Initializable, DaggerInjected {

    override fun init() {
        inject()
    }
}
