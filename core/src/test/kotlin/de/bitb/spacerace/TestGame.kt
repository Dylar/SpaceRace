package de.bitb.spacerace

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.injection.TestComponent
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.components.DaggerAppComponent

class TestGame : MainGame() {

    companion object {
        lateinit var testComponent: TestComponent
    }

    init {
//        testComponent = appComponent as TestComponent
    }

    override fun initGame() {
        super.initGame()
        testComponent = appComponent as TestComponent
        testComponent.inject(this)
    }

//    override fun initComponent(): AppComponent =
//            DaggerTestComponent.builder()
//                .build()

}

val game: TestGame
    get() = TestGame()
