package de.bitb.spacerace.injection

import dagger.Component
import de.bitb.spacerace.TestGame
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.usecase.game.NextPhaseUsecaseTest
import javax.inject.Scope


@FragmentScope
@Component(
        dependencies = [AppComponent::class],
        modules = [TestModule::class])
interface TestComponent {
//    fun inject(game: TestGame)
    fun inject(nextPhaseUsecaseTest: NextPhaseUsecaseTest)

}

/**
 * Scope for a lifetime of a fragment
 */
@Scope
annotation class FragmentScope