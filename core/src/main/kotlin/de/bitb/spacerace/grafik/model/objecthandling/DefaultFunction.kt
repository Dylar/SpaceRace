package de.bitb.spacerace.grafik.model.objecthandling

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Image

fun GameObject.getDisplayImage(
        posX: Float = 0f,
        posY: Float = 0f,
        color: Color = Color(1f, 1f, 1f, 1f),
        actor: Actor = Actor()
): Image {
    val image = object : Image(getGameImage().animation.region) {
        override fun act(delta: Float) {
            super.act(delta)
            actor.act(delta)
        }
    }
    image.setOrigin(image.width / 2, image.height / 2)
    image.setPosition(posX - image.width / 2, posY - image.height / 2)
    image.color = color
    return image
}

fun getRunnableAction(runnable: Runnable): RunnableAction {
    val action = RunnableAction()
    action.runnable = runnable
    return action
}
