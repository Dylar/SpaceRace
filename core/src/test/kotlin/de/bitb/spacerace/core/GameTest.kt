package de.bitb.spacerace.core

import com.badlogic.gdx.Application
import de.bitb.spacerace.config.DEBUG_GIFT_ITEMS
import de.bitb.spacerace.config.DEBUG_PLAYER_ITEMS
import org.junit.After
import org.junit.Before


open class GameTest {
    // This is our "test" application
    private var application: Application? = null
//    protected var testGame: TestGame? = null

    // Before running any tests, initialize the application with the headless backend
    @Before
    open fun setup() {
        // Note that we don't need to implement any of the listener's methods

//        game.also {
//            testGame = it
//            application = HeadlessApplication(it)
//        }
//        testGame = game
//        testGame?.also {
//            it.initGame() //TODO do it?
//            Logger.println(it)
//        }
//        application = HeadlessApplication(object : ApplicationListener {
//            override fun create() {
//                testGame?.create()
//                Logger.println("HEADLESS CREATE")
//
//            }
//
//            override fun resize(width: Int, height: Int) {
//                testGame?.resize(width, height)
//            }
//
//            override fun render() {
//                Logger.println("HEADLESS RENDER")
//                testGame?.render()
//
//            }
//
//            override fun pause() {
//                testGame?.pause()
//            }
//
//            override fun resume() {
//                testGame?.resume()
//            }
//
//            override fun dispose() {
//                testGame?.dispose()
//            }
//        })

        // Use Mockito to mock the OpenGL methods since we are running headlessly
//        Gdx.gl20 = TestGl20()
//        Gdx.gl = Gdx.gl20
    }

    // After we are done, clean up the application
    @After
    open fun teardown() {
        DEBUG_PLAYER_ITEMS = listOf()
        DEBUG_GIFT_ITEMS = listOf()
        // Exit the application first
        application?.exit()
        application = null

    }
}
