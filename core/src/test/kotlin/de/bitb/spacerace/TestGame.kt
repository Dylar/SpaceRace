package de.bitb.spacerace

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.components.DaggerTestComponent
import de.bitb.spacerace.injection.components.TestComponent
import de.bitb.spacerace.injection.modules.ApplicationModule

class TestGame : MainGame() {

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
            .build()

}

val game: TestGame
    get() = TestGame()
