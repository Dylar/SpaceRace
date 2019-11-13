package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.ui.player.PlayerStatsGui
import de.bitb.spacerace.ui.screens.game.control.DebugGui
import de.bitb.spacerace.ui.screens.game.control.GameActionGui
import de.bitb.spacerace.ui.screens.game.control.GameActionGuiNew
import de.bitb.spacerace.ui.screens.game.control.ViewControlGui
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table
import com.kotcrab.vis.ui.widget.CollapsibleWidget
import com.badlogic.gdx.utils.BooleanArray
import de.bitb.spacerace.core.utils.Logger


class GameGuiStage(
        screen: GameScreen
) : BaseGuiStage(screen) {

    @Inject
    protected lateinit var observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase

    private var dispo: Disposable? = null

    private var playerStatsGui: PlayerStatsGui = PlayerStatsGui(this)
    private var viewControlGui: ViewControlGui = ViewControlGui(screen)
    private var gameActionGui: GameActionGui = GameActionGui(this)
    private var debugGui: DebugGui = DebugGui(screen)

    private var gameActionGuiNew: GameActionGuiNew = GameActionGuiNew(this)

    init {
        MainGame.appComponent.inject(this)

//        addGameActionGui()




        listenToUpdate()

        addActor(playerStatsGui)
        addActor(viewControlGui)
        addActor(gameActionGuiNew)
        addActor(debugGui)
        debugGui.x = viewControlGui.width
    }

//    private fun addGameActionGui() {
//        val collapsibleWidget = CollapsibleWidget(gameActionGuiNew)
//
//        gameActionGuiNew.addListener(object : ChangeListener() {
//            override fun changed(event: ChangeEvent, actor: Actor) {
//                Logger.justPrint("changed")
//                collapsibleWidget.isCollapsed = !collapsibleWidget.isCollapsed
//            }
//        })
//        addActor(collapsibleWidget)
//    }

    private fun listenToUpdate() {
        dispo?.dispose()
        dispo = observeCurrentPlayerUseCase.observeStream(
                onNext = {
                    //                    Logger.println("observeCurrentPlayerUseCase NEXT:\n$it")
                    playerStatsGui.update(it)
                })
    }

}