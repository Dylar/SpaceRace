package de.bitb.spacerace.base

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Vector3
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.grafik.model.objecthandling.PositionData

val SCREEN_INPUT_POSITION: PositionData = PositionData()
    get() = field.apply {
        posX = Gdx.input.x.toFloat()
        posY = Gdx.input.y.toFloat()
    }

//MAYBE BULLSHIT - pseudo backing field
private val WORLD_INPUT_POSITION: PositionData = PositionData()
//NOT THIS
fun getWorldInputCoordination(camera: Camera): PositionData {
   val screenInput = SCREEN_INPUT_POSITION
    val vector = camera.unproject(Vector3(screenInput.posX, screenInput.posY, 0f))
    return WORLD_INPUT_POSITION.apply { posX = vector.x; posY = vector.y }
}

abstract class BaseGame : Game() {


    override fun create() {
        Gdx.input.isCatchBackKey = true
        initGame()
        initScreen()
    }

    abstract fun initGame()
    abstract fun initScreen()

    fun clearScreen(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 1f) {
        Gdx.gl.glClearColor(red, green, blue, alpha)
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT and GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun render() {
        clearScreen()
        super.render()
    }

}