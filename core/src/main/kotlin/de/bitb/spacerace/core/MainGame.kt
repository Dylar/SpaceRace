package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.gameover.GameOverCommand
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.components.DaggerAppComponent
import de.bitb.spacerace.injection.modules.ApplicationModule
import de.bitb.spacerace.injection.modules.DatabaseModule
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.ui.screens.GameOverScreen
import de.bitb.spacerace.ui.screens.game.GameStage
import de.bitb.spacerace.ui.screens.start.StartScreen
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import de.bitb.spacerace.usecase.game.observe.ObserveRoundUsecase
import de.bitb.spacerace.usecase.game.observe.ObserveWinnerUsecase
import de.bitb.spacerace.usecase.ui.CommandUsecase
import de.bitb.spacerace.usecase.ui.ObserveCommandUsecase
import de.bitb.spacerace.utils.Logger
import io.objectbox.Box
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
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
    protected lateinit var commandUsecase: CommandUsecase

    @Inject
    lateinit var observeWinnerUsecase: ObserveWinnerUsecase
    @Inject
    lateinit var observeRoundUsecase: ObserveRoundUsecase

    @Inject
    lateinit var observeCommandUsecase: ObserveCommandUsecase
    @Inject
    lateinit var observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase
    @Inject
    lateinit var playerController: PlayerController

    private var dispo: Disposable? = null

    open fun initComponent(): AppComponent =
            DaggerAppComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .databaseModule(DatabaseModule())
                    .build()

    private fun initObserver() {
        dispo?.dispose()
        dispo = observeCurrentPlayerUseCase.observeStream(
                onNext = {
                    playerController.currentPlayerData = it
                })

        commandUsecase.observeStream()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveCommand(event: BaseCommand) {
        commandUsecase.commandDispender.publishUpdate(event)
    }

    override fun initGame() {
        EventBus.getDefault().register(this)
        appComponent = initComponent()
        appComponent.inject(this)

        initObserver()
//        testFields()
    }

    override fun initScreen() {
        setScreen(StartScreen(this))
//        setScreen(GameScreen(this))
//        setScreen(GameOverScreen(this))
    }

    @Inject
    lateinit var box: Box<FieldData>

    private fun testFields() {
        Pair(
                FieldData(fieldType = FieldType.MINE),
                FieldData(fieldType = FieldType.MINE,
                        owner = PlayerColor.NAVY)
        ).also { (mineNot, mineOwned) ->
            box.put(listOf(
                    mineNot.apply {
                        connections.add(mineOwned)
                    },
                    mineOwned.apply {
                        connections.add(mineNot)
                    }))
        }.also { (notLocal, ownLocal) ->
            val all = box.all
            val notDb = all[0]
            val ownDb = all[1]
            val conOwn = ownLocal.connections[0]
            val conNot = notLocal.connections[0]
            val conOwnDb = ownDb.connections[0]
            val conNotDb = notDb.connections[0]
            Logger.println("conOwn: ", conOwn)
            Logger.println("conNot: ", conNot)
            Logger.println("conOwnDb: ", conOwnDb)
            Logger.println("conNotDb: ", conNotDb)
            Logger.println("RESULT: ", all)
        }
    }

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
        compositeDisposable.clear()
        (screen as? BaseScreen)?.clear()
    }

    fun startGameDELETE_ME() {

//        TODO do this as command in "start GameScreen"

        val gameStage = (screen as BaseScreen).gameStage as GameStage
        gameStage.addEntitiesToMap()

//        TODO observe somewhere

        initGameObserver()
    }

    fun initGameObserver() {
        initWinnerObserver()
        initPhaseObserver()
        initGameOverObserver()
    }

    private fun initGameOverObserver() {
        observeCommandUsecase.observeStream { event ->
            when (event) {
                is GameOverCommand -> {
                    clear()
                    changeScreen(GameOverScreen(this))
                }
            }
        }
    }

    fun initWinnerObserver() {
        compositeDisposable += observeWinnerUsecase.observeStream(
                params = WIN_AMOUNT,
                onNext = { winner ->
                    Logger.println("AND THE WINNER IIIIISSS: $winner")
                    EventBus.getDefault().post(GameOverCommand(winner.playerColor))
                })
    }

    fun initPhaseObserver() {
        compositeDisposable += observeRoundUsecase.observeStream(
                onNext = { isNext ->
                    //                    Logger.println("observeRoundUsecase: NEXT ROUND: $isNext")
                })
    }

}