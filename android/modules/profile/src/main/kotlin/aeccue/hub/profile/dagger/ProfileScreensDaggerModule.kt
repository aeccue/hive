package aeccue.hub.profile.dagger

import aeccue.foundation.android.screen.NavHostScreen
import dagger.Module
import dagger.multibindings.Multibinds
import javax.inject.Named

@Module
interface ProfileScreensDaggerModule {

    companion object {

        const val SCREENS = "ProfileScreens.Screens"
    }

    @Named(SCREENS)
    @Multibinds
    fun screens(): Set<NavHostScreen>
}
