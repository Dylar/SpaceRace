package de.bitb.spacerace.injection.components

import dagger.Component
import de.bitb.spacerace.injection.modules.*
import de.bitb.spacerace.ui.player.PlayerStats
import de.bornholdtlee.baseproject.injection.ApplicationScope

@ApplicationScope
@Component(modules = [
    ApplicationModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    UtilsModule::class,
    ControllerModule::class,
    BuilderModule::class])
interface AppComponent {
    fun inject(playerStats: PlayerStats)
}