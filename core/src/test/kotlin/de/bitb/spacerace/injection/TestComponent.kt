package de.bitb.spacerace.injection

import dagger.Component
import de.bitb.spacerace.TestGame
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.usecase.game.NextPhaseUsecaseTest
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(
        modules = [TestModule::class])
interface TestComponent : AppComponent {

//    fun provideGame(): TestGame
//
//    fun providesPlayerDataSource(box: Box<PlayerData>): PlayerDataSource
//    fun providesMapDataSource(fieldBox: Box<FieldData>): MapDataSource
//
//    fun providesBoxStore(): BoxStore
//    fun providesFieldDataBox(): Box<FieldData>
//    fun providesPlayerDataBox(): Box<PlayerData>
//
//    fun providePlayerController() = PlayerController()
//    fun provideFieldController(playerController: PlayerController): FieldController
//    fun provideInputHandler(): InputHandler
//
//    fun providePlayerColorDispender(): PlayerColorDispender
//    fun provideCommandDispender(): CommandDispender

    fun inject(game: TestGame)
    fun inject(nextPhaseUsecaseTest: NextPhaseUsecaseTest)

}

/**
 * Scope for a lifetime of a fragment
 */
@Scope
annotation class FragmentScope