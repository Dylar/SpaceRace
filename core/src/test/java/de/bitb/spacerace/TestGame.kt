package de.bitb.spacerace

import de.bitb.spacerace.core.MainGame

class TestGame : MainGame(), MockData {

    companion object {
        lateinit var testComponent: TestComponent
    }

    override fun initScreen() {
        super.initScreen()
//        testComponent = DaggerTestComponent.builder()
//                .appComponent(appComponent)
//                .build()
        testComponent.inject(this)

    }
}


val game: TestGame
    get() = TestGame()
