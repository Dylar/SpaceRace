package de.bitb.spacerace.game

import de.bitb.spacerace.config.VERSION_TEST
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.injection.DaggerTestComponent
import de.bitb.spacerace.env.TestSystemSetup
import de.bitb.spacerace.core.injection.components.AppComponent
import de.bitb.spacerace.core.injection.TestComponent
import de.bitb.spacerace.core.injection.modules.ApplicationModule
import de.bitb.spacerace.core.injection.modules.DatabaseModule


class TestGame : MainGame(VERSION_TEST, TestSystemSetup.createMockBoxStore()) {

    companion object {
        lateinit var testComponent: TestComponent
    }

    override fun initGame() {
        super.initGame()
        testComponent = appComponent as TestComponent
        testComponent.inject(this)
    }

    override fun initComponent(): AppComponent = DaggerTestComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .databaseModule(DatabaseModule(
                    boxStore = objBox
            ))
            .build()

}
