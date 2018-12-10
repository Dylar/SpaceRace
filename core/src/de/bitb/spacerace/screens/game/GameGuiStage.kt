package de.bitb.spacerace.screens.game

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.BaseSpace
import de.bitb.spacerace.ui.control.GameControl
import de.bitb.spacerace.ui.control.ViewControl
import de.bitb.spacerace.ui.game.EndRoundMenu
import de.bitb.spacerace.ui.game.RoundDetails
import de.bitb.spacerace.ui.player.ItemMenu
import de.bitb.spacerace.ui.player.PlayerStats

class GameGuiStage(val space: BaseSpace, screen: GameScreen) : BaseGuiStage() {

    internal var endRoundMenu = EndRoundMenu(space, this)
    private var roundDetails = RoundDetails(space, this)

    internal var playerStats: PlayerStats = PlayerStats(space)
    private var viewControl: ViewControl = ViewControl(space, screen)
    private var gameControl: GameControl = GameControl(space, this)

    init {
        addActor(playerStats)
        addActor(viewControl)
        addActor(gameControl)
    }

    fun openRoundDetails(ship: Player) {
        roundDetails.toggle(ship)
    }

    //TEST
    private class ItemsDialog : Dialog("Items", TextureCollection.skin, "default") {
        override fun result(result: Any?) {
            Logger.println("Result: $result")
        }
    }

    private fun openItemsDialog() {
        Logger.println("OPEN ITEMS")
        val dialog = ItemsDialog()
        dialog.text("Are you sure you want to yada yada?")
        for (item in space.currentPlayer.items) {
            item.toString()
            val image2 = TextureRegionDrawable(TextureRegion(TextureCollection.blackhole))
            val image1 = TextureRegionDrawable(TextureRegion(TextureCollection.blueField))
            val image3 = TextureRegionDrawable(TextureRegion(TextureCollection.yellowField))
            val btn = ImageButton(image1, image2, image3)

            dialog.button(btn, item)
        }
//        dialog.button("Yes", true) //sends "true" as the result
        dialog.button("No", false) //sends "false" as the result
        dialog.show(this)
    }

}