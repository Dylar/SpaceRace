package de.bitb.spacerace.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.model.background.FallingStar


class BackgroundStage(val space: BaseSpace, val screen: GameScreen) : BaseStage() {

    private var texture: Texture = TextureCollection.gameBackground

    init {
        startBombarding()
    }

    private fun startBombarding() {
        for (index in 0..5) {
            val star = FallingStar(startY = (Gdx.graphics.height / 2).toFloat(), endY = (Gdx.graphics.height / 2).toFloat())
            addActor(star)
        }
    }

    override fun draw() {
        clearColor()
        batch.begin()
        setColor(alpha = 0.9f)
        batch.draw(texture, 0f, 0f, posX.toInt(), posY.toInt(),
                (Gdx.graphics.width * (BaseScreen.MAX_ZOOM - screen.currentZoom + 1)).toInt(),
                (Gdx.graphics.height * (BaseScreen.MAX_ZOOM - screen.currentZoom + 1)).toInt())
        clearColor()
        batch.end()
        super.draw()
    }

}