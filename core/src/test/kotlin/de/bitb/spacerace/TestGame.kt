package de.bitb.spacerace

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.injection.TestComponent
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.modules.ApplicationModule

class TestGame : MainGame() {

    companion object {
        lateinit var testComponent: TestComponent
    }

    init {
//        testComponent = appComponent as TestComponent
    }

    override fun initScreen() {
        super.initScreen()
//        testComponent = DaggerTestComponent.builder()
//                .appComponent(appComponent)
//                .build()
//        testComponent.inject(this)
    }

//    override fun initComponent(): AppComponent =
//            DaggerTestComponent.builder()
//                .build()

}

val game: TestGame
    get() = TestGame()
