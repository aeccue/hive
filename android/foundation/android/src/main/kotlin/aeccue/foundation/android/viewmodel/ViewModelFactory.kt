/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.viewmodel

import aeccue.foundation.dagger.scope.ScreenContainerScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

@ScreenContainerScope
class ViewModelFactory @Inject constructor(
    private val providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var viewModel = providers[modelClass]
        if (viewModel == null) {
            for ((k, p) in providers) {
                if (modelClass.isAssignableFrom(k)) {
                    viewModel = p
                    break
                }
            }
        }

        if (viewModel == null) {
            throw IllegalArgumentException("ViewModel factory for class $modelClass does not exist")
        }

        return viewModel.get() as T
    }
}
