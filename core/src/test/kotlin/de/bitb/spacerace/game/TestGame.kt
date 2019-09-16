package de.bitb.spacerace.game

import de.bitb.spacerace.config.dimensions.Dimensions.IS_TEST
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.env.Setup
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.components.DaggerTestComponent
import de.bitb.spacerace.injection.components.TestComponent
import de.bitb.spacerace.injection.modules.ApplicationModule
import de.bitb.spacerace.injection.modules.DatabaseModule

class
TestGame : MainGame() {

    companion object {
        lateinit var testComponent: TestComponent
    }

    override fun initGame() {
        super.initGame()
        IS_TEST = true
        testComponent = appComponent as TestComponent
        testComponent.inject(this)
    }

    override fun initComponent(): AppComponent = DaggerTestComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .databaseModule(DatabaseModule(
                    mockDb = Setup.createMockBoxStore()
            ))
            .build()

}
