package de.bitb.spacerace.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.BaseSpace
import de.bitb.spacerace.model.FallingStar


class BackgroundStage(val space: BaseSpace, val screen: GameScreen) : BaseStage() {

    private var texture: Texture = TextureCollection.gameBackground

    init {
        val star1 = FallingStar(startY = (Gdx.graphics.height / 2).toFloat(), endY = (Gdx.graphics.height / 2).toFloat())
        addActor(star1)
        val star2 = FallingStar(startY = (Gdx.graphics.height / 3).toFloat(), endY = (Gdx.graphics.height / 3).toFloat())
        addActor(star2)
        val star3 = FallingStar(startY = (Gdx.graphics.height / 4).toFloat(), endY = (Gdx.graphics.height / 4).toFloat())
        addActor(star3)
        val star4 = FallingStar(startY = (Gdx.graphics.height / 5).toFloat(), endY = (Gdx.graphics.height / 5).toFloat())
        addActor(star4)
    }

    override fun draw() {
        clearColor()
        batch.begin()
        batch.draw(texture, 0f, 0f, posX.toInt(), posY.toInt(),
                (Gdx.graphics.width * (BaseScreen.MAX_ZOOM - screen.currentZoom + 1)).toInt(),
                (Gdx.graphics.height * (BaseScreen.MAX_ZOOM - screen.currentZoom + 1)).toInt())
        batch.end()
        super.draw()
    }

}