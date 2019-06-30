package de.bitb.spacerace

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.injection.TestComponent

class TestGame : MainGame(), MockData {

    companion object {
        lateinit var testComponent: TestComponent
    }

    override fun initScreen() {
        super.initScreen()
//        testComponent = DaggerTestComponent.builder()
//                .appComponent(appComponent)
//                .build()
//        testComponent.inject(this)

    }
}


val game: TestGame
    get() = TestGame()
