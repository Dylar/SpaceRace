package de.bitb.spacerace.core.injection

import dagger.Component
import de.bitb.spacerace.core.injection.components.AppComponent
import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.core.injection.modules.*
import de.bitb.spacerace.tests.ObjBoxRelationTest
import de.bitb.spacerace.tests.items.ItemsTest
import de.bitb.spacerace.tests.usecase.action.NextPhaseUsecaseTest
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ApplicationModule::class,
//    NetworkModule::class,
            DatabaseModule::class,
            GameModule::class,
            EditorModule::class,
            UseCaseModule::class,
//    UtilsModule::class,
//    BuilderModule::class,
            ControllerModule::class]
)
interface TestComponent : AppComponent {

    fun inject(testEnvironment: TestEnvironment)
    fun inject(game: TestGame)

    fun inject(nextPhaseUsecaseTest: NextPhaseUsecaseTest)
    fun inject(objBoxRelationTest: ObjBoxRelationTest)
    fun inject(itemsTest: ItemsTest)

}