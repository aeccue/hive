/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.module

import aeccue.foundation.android.viewmodel.ViewModelFactory
import aeccue.foundation.dagger.scope.ScreenContainerScope
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
interface ViewModelsDaggerModule {

    @ScreenContainerScope
    @Binds
    fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
