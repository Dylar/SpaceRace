package de.bitb.spacerace

import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.headless.HeadlessApplication
import io.mockk.mockk
import net.bytebuddy.implementation.bind.annotation.SuperCall
import org.junit.After
import org.junit.Before


open class GameTest {
    // This is our "test" application
    private var application: Application? = null
    protected var testGame: TestGame? = null

    // Before running any tests, initialize the application with the headless backend
    @Before
    open fun setup() {
        // Note that we don't need to implement any of the listener's methods

//        game.also {
//            testGame = it
//            application = HeadlessApplication(it)
//        }
        testGame = game
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
        // Exit the application first
        application?.exit()
        application = null
    }
}
