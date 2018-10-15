package de.bitb.spacerace.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.BaseSpace


class BackgroundStage(val space: BaseSpace, val screen: GameScreen) : BaseStage() {

    private var texture: Texture = TextureCollection.gameBackground

    override fun draw() {
        super.draw()
        batch.begin()
        batch.draw(texture, 0f, 0f, posX.toInt(), posY.toInt(),
                (Gdx.graphics.width * (BaseScreen.MAX_ZOOM - screen.currentZoom + 1)).toInt(),
                (Gdx.graphics.height * (BaseScreen.MAX_ZOOM - screen.currentZoom + 1)).toInt())
        batch.end()
    }


}