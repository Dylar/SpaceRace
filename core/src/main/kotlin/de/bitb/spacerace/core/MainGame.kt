package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.events.GameOverEvent
import de.bitb.spacerace.events.OpenEndRoundMenuEvent
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.components.DaggerAppComponent
import de.bitb.spacerace.injection.modules.ApplicationModule
import de.bitb.spacerace.injection.modules.DatabaseModule
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.ui.screens.GameOverScreen
import de.bitb.spacerace.ui.screens.game.GameStage
import de.bitb.spacerace.ui.screens.start.StartScreen
import de.bitb.spacerace.usecase.game.observe.ObserveRoundUsecase
import de.bitb.spacerace.usecase.game.observe.ObserveWinnerUsecase
import de.bitb.spacerace.usecase.ui.CommandUsecase
import de.bitb.spacerace.utils.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

open class MainGame : BaseGame() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var observeWinnerUsecase: ObserveWinnerUsecase
    @Inject
    lateinit var observeRoundUsecase: ObserveRoundUsecase
    @Inject
    protected lateinit var commandUsecase: CommandUsecase
    @Inject
    lateinit var playerController: PlayerController
    @Inject
    lateinit var graphicController: GraphicController

    open fun initComponent(): AppComponent =
            DaggerAppComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .databaseModule(DatabaseModule())
                    .build()

    override fun initGame() {
        EventBus.getDefault().register(this)
        appComponent = initComponent()
        appComponent.inject(this)

        initObserver()
    }

    private fun initObserver() {
        playerController.initObserver()
        commandUsecase.observeStream()
    }

    override fun initScreen() {
        setScreen(StartScreen(this))
//        setScreen(GameScreen(this))
//        setScreen(GameOverScreen(this))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveCommand(event: BaseCommand) {
        commandUsecase.commandDispender.publishUpdate(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun gameOverEvent(event: GameOverEvent) {
        clear()
        changeScreen(GameOverScreen(this))
    }

//
//    ██╗  ██╗███████╗██╗   ██╗███████╗
//    ██║ ██╔╝██╔════╝╚██╗ ██╔╝██╔════╝
//    █████╔╝ █████╗   ╚████╔╝ ███████╗
//    ██╔═██╗ ██╔══╝    ╚██╔╝  ╚════██║
//    ██║  ██╗███████╗   ██║   ███████║
//    ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝
//

    override fun render() {
        handleSystemInput()
        super.render()
    }

    private fun handleSystemInput() {
        if (isCloseGameTipped()) {
            Gdx.app.exit()
        } else if (isBackTipped()) {
            val previousScreen = (screen as BaseScreen).previousScreen
            (screen as BaseScreen).clear()

            if (previousScreen == null) {
                Gdx.app.exit()
            } else {
                changeScreen(previousScreen)
            }
        }
    }

    private fun checkCombi(vararg keys: Int): Boolean {
        var oneJustPressed = false
        var allPressed = true
        keys.forEach { key ->
            run {
                oneJustPressed = oneJustPressed || Gdx.input.isKeyJustPressed(key)
                allPressed = allPressed && Gdx.input.isKeyPressed(key)
            }
        }
        return oneJustPressed && allPressed
    }

    private fun isCloseGameTipped(): Boolean {
        return checkCombi(Input.Keys.SYM, Input.Keys.W)
    }

    private fun isBackTipped(): Boolean {
        return checkCombi(Input.Keys.ALT_LEFT, Input.Keys.TAB) || Gdx.input.isKeyJustPressed(Input.Keys.BACK)
    }

    //TODO clear on
    fun clear() {
        playerController.clear()
        compositeDisposable.clear()
        (screen as? BaseScreen)?.clear()
    }

    fun addEntities() {
        val gameStage = (screen as BaseScreen).gameStage as GameStage
        gameStage.clear()
        gameStage.addEntitiesToMap()
    }

    fun initGameObserver() {
        initWinnerObserver()
        initPhaseObserver()
    }

    fun initWinnerObserver() {
        compositeDisposable += observeWinnerUsecase.observeStream(
                params = WIN_AMOUNT,
                onNext = { winner ->
                    Logger.println("AND THE WINNER IIIIISSS: $winner")
                    EventBus.getDefault().post(GameOverEvent(winner.playerColor))
                })
    }

    fun initPhaseObserver() {
        compositeDisposable += observeRoundUsecase.observeStream { roundEnd ->
            if (roundEnd) EventBus.getDefault().post(OpenEndRoundMenuEvent())
        }
    }


    //TODO RETURN MAPDATA

    fun initDefaultMap(defaultMap: SpaceMap): MapData = MapData().apply {
        //create fields
        defaultMap.groups.forEach { spaceGroup ->
            spaceGroup.fields.entries.forEach { field ->
                val spaceField = field.value
                val fieldData = FieldConfigData(
                        fieldType = spaceField.fieldType,
                        gamePosition = spaceField.gamePosition)
                fields.add(fieldData)
                if (spaceField.gamePosition.isPosition(defaultMap.startField.gamePosition))
                    startPosition.setPosition(fieldData.gamePosition)
            }
        }

        fields.forEach { fieldData ->
            defaultMap.connections
                    .filter { it.isConnected(fieldData.gamePosition) }
                    .forEach { connection ->
                        val opposite = connection.getOpposite(fieldData.gamePosition)
                        val oppositeData = fields.find { field -> field.gamePosition.isPosition(opposite.gamePosition) }
                        oppositeData?.also {
                            fieldData.connections.add(it.gamePosition)
                        }
                    }
        }

    }

}