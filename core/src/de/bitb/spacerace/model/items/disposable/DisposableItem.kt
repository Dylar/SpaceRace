package de.bitb.spacerace.model.items.disposable

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.items.disposable.moving.MovingObject
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.player.PlayerColor

abstract class DisposableItem(owner: PlayerColor, price: Int) : Item(owner, price) {

    val speed = Math.random()
    var attachedTo = PlayerColor.NONE
    var gameImage: MovingObject? = null

    private fun initImage() {
        gameImage = MovingObject(img, MovingState.ROTATE_POINT, owner.color)

//                object : Image(texture) {
//            init {
//                setOrigin(width / 2, height / 2)
//                color = owner.color
//            }
//
//            var angle = 0f
//            var point = Vector2()
//
//            val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()
//
//            override fun act(delta: Float) {
//                super.act(delta)
//
//                if (actions.isEmpty) angle += slice * speed.toFloat() * delta else angle = 0f
//                point = CalculationUtils.calculateRotationPoint(Vector2(fieldPosition!!.getAbsolutX() - width / 2, fieldPosition!!.getAbsolutY() - height / 2), (width * 2).toDouble(), angle.toDouble())
//
//            }
//
//            override fun draw(batch: Batch?, parentAlpha: Float) {
//                if (actions.isEmpty) {
//                    setPosition(point.x, point.y)
//                }
//                super.draw(batch, parentAlpha)
//            }
//        }
    }

    override fun canUse(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> getPlayerData(game, player).phase.isMain()
            ItemState.DISPOSED, ItemState.USED -> player != owner && getPlayerData(game, player).phase.isMain2()
            else -> false
        }
    }

    override fun use(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                initImage()
//                getPlayerData(game, player).fieldPosition.disposeItem(this)
                getPlayerData(game, player).playerItems.disposeItem(this)
                true
            }
            ItemState.DISPOSED -> {
                attachedTo = player
                getPlayerData(game, player).playerItems.attachItem(this)
//                getPlayerData(game, player).fieldPosition.attachItem(this)
                true
            }
            else -> true
        }
    }

}