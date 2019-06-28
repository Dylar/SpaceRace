package de.bitb.spacerace

import dagger.Component
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.usecase.game.NextPhaseUsecaseTest
import javax.inject.Scope


@SingletonTest
@Component(dependencies = [AppComponent::class])
interface TestComponent {
    fun inject(game: TestGame)
    fun inject(nextPhaseUsecaseTest: NextPhaseUsecaseTest)

}

/**
 * Identifies a type that the injector only instantiates once. Not inherited.
 *
 * @see javax.inject.Scope @Scope
 */
@Scope
@MustBeDocumented
@kotlin.annotation.Retention
annotation class SingletonTest