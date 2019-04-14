package de.bitb.spacerace.controller

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.usecase.LoadPlayerUsecase
import io.objectbox.Box
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class GameController(game: MainGame) {
    val inputHandler = InputHandler(game)
    val playerController = PlayerController()
    val fieldController = FieldController(playerController)

    val victories: MutableMap<PlayerColor, Int> = HashMap()
    val gamePlayer: MutableList<PlayerColor> = ArrayList()
    var spaceMap: MapCollection = MapCollection.RANDOM
    var currentGoal: SpaceField = SpaceField.NONE
    //
    @Inject
    protected lateinit var loadPlayerUsercase: LoadPlayerUsecase

    @Inject
    protected lateinit var box: Box<PlayerData>

    lateinit var map: SpaceMap

    init {
        MainGame.appComponent.inject(this)
        PlayerColor.values().forEach { field -> victories[field] = 0 }
    }

    fun initGame(map: SpaceMap) {
        this.map = map
        setRandomGoal()
        fieldController.initMap(this, map)

        val startField = map.startField
        //TODO make load game
        var compositeDisposable = CompositeDisposable()
        for (playerColor in gamePlayer.withIndex()) {
            compositeDisposable += loadPlayerUsercase(
                    PlayerData(playerColor = playerColor.value),
                    onNext = {
                        //                        Gdx.app.postRunnable {
                        //            val player = Player(PlayerData(playerColor = playerColor.value))
                        val player = Player(it)

                        playerController.players.add(player)
                        playerController.playerMap[playerColor.value] = player

                        player.playerImage.movingSpeed * playerColor.index
                        fieldController.addShip(player, startField)
//                        }
                    },
                    onError = {

                    })
        }
    }

    fun setRandomGoal() {
        currentGoal.fieldImage.setBlinkColor(null)
        currentGoal = map.getRandomGoal()
        currentGoal.fieldImage.setBlinkColor(Color(currentGoal.fieldType.color))
    }

    fun getWinner(): PlayerColor {
        var winner: PlayerColor = PlayerColor.NONE
        victories.forEach { entrySet ->
            if (entrySet.value == WIN_AMOUNT) {
                winner = entrySet.key
                return@forEach
            }
        }
        return winner
    }

}

