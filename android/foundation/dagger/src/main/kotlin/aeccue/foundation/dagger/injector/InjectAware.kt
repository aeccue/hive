/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

/**
 * Indicates that the implementing class is aware that it is injectable and should have its
 * [preInject] and [postInject] called accordingly.
 */
interface InjectAware {

    /**
     * This method is invoked before dependencies have been injected.
     */
    fun preInject() {}

    /**
     * This method is invoked after dependencies have been injected.
     */
    fun postInject() {}
}
