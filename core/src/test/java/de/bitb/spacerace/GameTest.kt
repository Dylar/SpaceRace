package de.bitb.spacerace

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.headless.HeadlessApplication
import io.mockk.mockk
import org.junit.After
import org.junit.Before


open class GameTest {
    // This is our "test" application
    private var application: Application? = null

    // Before running any tests, initialize the application with the headless backend
    @Before
    open fun setup() {
        // Note that we don't need to implement any of the listener's methods

        game.also { application = HeadlessApplication(it) }

//        application = HeadlessApplication(object : ApplicationListener {
//            override fun create() {
//
//            }
//
//            override fun resize(width: Int, height: Int) {}
//            override fun render() {}
//            override fun pause() {}
//            override fun resume() {}
//            override fun dispose() {}
//        })

        // Use Mockito to mock the OpenGL methods since we are running headlessly
        Gdx.gl20 = mockk()
        Gdx.gl = Gdx.gl20
    }

    // After we are done, clean up the application
    @After
    open fun teardown() {
        // Exit the application first
        application!!.exit()
        application = null
    }
}
