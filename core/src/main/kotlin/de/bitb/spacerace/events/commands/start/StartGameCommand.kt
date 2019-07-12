package de.bitb.spacerace.events.commands.start

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.player.MoveCommand
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.usecase.init.LoadPlayerUsecase
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class StartGameCommand() : BaseCommand() {

    @Inject
    protected lateinit var loadPlayerUsecase: LoadPlayerUsecase

    @Inject
    protected lateinit var game: MainGame

    @Inject
    protected lateinit var playerController: PlayerController

    @Inject
    protected lateinit var fieldController: FieldController

    @Inject
    lateinit var inputHandler: InputHandler

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return SELECTED_PLAYER.size > 1
    }

    override fun execute() {

        Logger.println("EXECUTE StartGameCommand")
        inputHandler.removeListener()
        game.changeScreen(GameScreen(game, game.screen as BaseScreen))
        loadPlayerUsecase.getResult(
                SELECTED_PLAYER,
                onSuccess = { players ->
                    //TODO make load game
                    Logger.println("NEXT: StartGameCommand")

                    val map = initMap()

                    playerController.clearPlayer()
                    val startField = map.startField
                    players.withIndex()
                            .forEach {
                                addPlayer(it, startField)
                            }

                    game.startGameDELETE_ME()
                })
    }


    private fun initMap(): SpaceMap {
        fieldController.clearField()
        return fieldController.spaceMap
                .createMap()
                .also {
                    fieldController.map = it
                    fieldController.setRandomGoal()
                    addFields(*it.groups.toTypedArray())
                }
    }

    private fun addFields(vararg spaceGroups: SpaceGroup) {
        for (spaceGroup in spaceGroups) {
            for (field in spaceGroup.fields.entries.withIndex()) {
                addField(field.value.value)
            }
        }
    }

    private fun addField(spaceField: SpaceField) {
        spaceField.getGameImage().addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(MoveCommand(spaceField, playerController.currentPlayerData))
                return true
            }
        })
        fieldController.fields.add(spaceField)
        fieldController.addFieldMap(spaceField)
    }

    private fun addPlayer(playerData: IndexedValue<PlayerData>, startField: SpaceField) {
        val player = Player(playerData.value.playerColor)

        playerController.addPlayer(player)
//        player.playerImage.movingSpeed * playerData.index
        fieldController.addShip(player, startField)
    }


}