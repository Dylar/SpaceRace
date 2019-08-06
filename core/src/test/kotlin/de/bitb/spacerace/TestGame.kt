package de.bitb.spacerace

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.injection.TestComponent
import de.bitb.spacerace.injection.components.AppComponent

class TestGame : MainGame() {

    companion object {
        lateinit var testComponent: TestComponent
    }

    init {
//        testComponent = appComponent as TestComponent
    }

    override fun initScreen() {
        super.initScreen()
        testComponent = appComponent as TestComponent
        testComponent.inject(this)
    }

    override fun initComponent(): AppComponent =
            DaggerTestComponent.builder()
                .build()

}

val game: TestGame
    get() = TestGame()
