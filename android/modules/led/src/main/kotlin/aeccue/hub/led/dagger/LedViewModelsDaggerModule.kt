package aeccue.hub.led.dagger

import aeccue.foundation.dagger.scope.ScreenScope
import aeccue.hub.led.viewmodel.LedSectionViewModel
import aeccue.hub.led.viewmodel.LedSetViewModel
import aeccue.hub.led.viewmodel.LedSetupsViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides

@Module
interface LedViewModelsDaggerModule {

    companion object {

        @ScreenScope
        @Provides
        fun setupViewModel(provider: ViewModelProvider): LedSetupsViewModel =
            provider[LedSetupsViewModel::class.java]

        @ScreenScope
        @Provides
        fun setViewModel(provider: ViewModelProvider): LedSetViewModel =
            provider[LedSetViewModel::class.java]

        @ScreenScope
        @Provides
        fun sectionViewModel(provider: ViewModelProvider): LedSectionViewModel =
            provider[LedSectionViewModel::class.java]
    }
}
