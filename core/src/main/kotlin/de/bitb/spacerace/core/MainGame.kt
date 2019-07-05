package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.components.DaggerAppComponent
import de.bitb.spacerace.injection.modules.ApplicationModule
import de.bitb.spacerace.injection.modules.DatabaseModule
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.ui.screens.start.StartScreen
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import de.bitb.spacerace.usecase.ui.CommandUsecase
import io.objectbox.Box
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

open class MainGame : BaseGame() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    @Inject
    protected lateinit var commandUsecase: CommandUsecase

    @Inject
    lateinit var box: Box<FieldData>

    lateinit var gameController: GameController

    @Inject
    lateinit var inputHandler: InputHandler

    @Inject
    lateinit var observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase

    private var dispo: Disposable? = null

    init {
        EventBus.getDefault().register(this)
        appComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .databaseModule(DatabaseModule())
                .build()
    }

    private fun initObserver() {
        dispo?.dispose()
        dispo = observeCurrentPlayerUseCase.observeStream(
                onNext = {
                    //                    playerController.fixColor(it)
                })

        commandUsecase.observeStream(
                onNext = {
                    inputHandler.notifyObserver(it)
                }
        )
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveCommand(event: BaseCommand) {
        commandUsecase.commandDispender.publishUpdate(event)
    }

    override fun initScreen() {
        appComponent.inject(this)
        initObserver()
        setScreen(StartScreen(this))

//        testFields()
//        setScreen(GameScreen(this))
//        setScreen(GameOverScreen(this))
    }

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
            inputHandler.removeListener()
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
        gameController.compositeDisposable.clear()
        (screen as BaseScreen).clear()
    }

}