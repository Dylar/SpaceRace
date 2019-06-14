package de.bitb.spacerace.controller

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.gameover.GameOverCommand
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.ui.screens.game.GameStage
import de.bitb.spacerace.usecase.game.AnnounceWinnerUsecase
import de.bitb.spacerace.usecase.game.observe.ObservePlayerUsecase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class GameController() : DefaultFunction by object : DefaultFunction {} {
    val gamePlayer: MutableList<PlayerColor> = mutableListOf()

    val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var playerController: PlayerController

    @Inject
    lateinit var fieldController: FieldController

    @Inject
    lateinit var announceWinnerUsecase: AnnounceWinnerUsecase

    @Inject
    lateinit var observePlayerUsecase: ObservePlayerUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    fun initGame(game: MainGame, playerDatas: List<PlayerData>) {
        val map = fieldController.initMap(this)

        playerController.clearPlayer()
        val startField = map.startField
        playerDatas.withIndex().forEach{
            Logger.println("NEXT: loadPlayerUsecase: ${it.value}")
            addPlayer(it, startField)
        }
        val gameStage = (game.screen as BaseScreen).gameStage as GameStage
        gameStage.addEntitiesToMap()

        compositeDisposable += announceWinnerUsecase.observeStream(WIN_AMOUNT,
                onNext = {
                    it.firstOrNull()
                            ?.let { winner ->
                                Logger.println("AND THE WINNER IIIIISSS: $winner")
                                EventBus.getDefault().post(GameOverCommand(winner.playerColor))
                            }
                },
                onError = {
                    Logger.println("ERROR WINNER! HAHAHHAHAHAHAHAHHAHAHAHAHAHHAHAHAHAHAHHAHAHAHAHAHHAHAHAHAHAHHAHAHAHAHAHHAHAHA")
                })
    }


    private fun addPlayer(playerData: IndexedValue<PlayerData>, startField: SpaceField) {
        val player = Player(playerData.value)

        playerController.players.add(player)
//        player.playerImage.movingSpeed * playerData.index
        fieldController.addShip(player, startField)

//        observePlayerUsecase.observeStream(player.playerData.playerColor,
//                onNext = {
//                    player.playerData = it.first() //TODO necessary?
//                })
    }

}

