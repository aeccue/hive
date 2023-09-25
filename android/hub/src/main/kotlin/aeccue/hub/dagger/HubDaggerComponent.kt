/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.hub.dagger

import aeccue.foundation.dagger.component.BaseDaggerComponent
import aeccue.foundation.dagger.module.DaggerInjectorModule
import aeccue.foundation.dagger.scope.ApplicationScope
import aeccue.foundation.dagger.scope.ComponentScope
import aeccue.hub.app.HubApplication
import dagger.Component

@ComponentScope
@ApplicationScope
@Component(
    modules = [
        DaggerInjectorModule::class,
        HubDaggerModule::class
    ]
)
abstract class HubDaggerComponent : BaseDaggerComponent<HubApplication>() {

    @Component.Factory
    interface Factory : BaseDaggerComponent.Factory<HubApplication>
}
