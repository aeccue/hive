package aeccue.hub.led.dagger

import aeccue.foundation.android.screen.NavHostScreen
import aeccue.foundation.dagger.scope.ScreenScope
import aeccue.hub.led.screen.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import dagger.multibindings.Multibinds
import javax.inject.Named

@Module
interface LedScreensDaggerModule {

    companion object {

        const val SCREENS = "LedScreensModule.Screens"
    }

    @Named(SCREENS)
    @Multibinds
    fun screens(): Set<NavHostScreen>

    @ScreenScope
    @Named(SCREENS)
    @Binds
    @IntoSet
    fun setupsScreen(screen: LedSetupsScreen): NavHostScreen

    @ScreenScope
    @Named(SCREENS)
    @Binds
    @IntoSet
    fun setScreen(screen: LedSetScreen): NavHostScreen

    @ScreenScope
    @Named(SCREENS)
    @Binds
    @IntoSet
    fun sectionScreen(screen: LedSectionScreen): NavHostScreen
}
