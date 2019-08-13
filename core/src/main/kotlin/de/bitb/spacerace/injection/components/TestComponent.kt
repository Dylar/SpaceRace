package de.bitb.spacerace.injection.components

import dagger.Component
import de.bitb.spacerace.TestGame
import de.bitb.spacerace.injection.modules.*
import de.bitb.spacerace.usecase.game.NextPhaseUsecaseTest
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ApplicationModule::class,
//    NetworkModule::class,
            DatabaseModule::class,
            GameModule::class,
            UseCaseModule::class,
//    UtilsModule::class,
//    BuilderModule::class,
            ControllerModule::class]
)
interface TestComponent : AppComponent {

    fun inject(game: TestGame)
    fun inject(nextPhaseUsecaseTest: NextPhaseUsecaseTest)

}