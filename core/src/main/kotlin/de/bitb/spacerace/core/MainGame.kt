package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.VERSION
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.env.createMap
import de.bitb.spacerace.events.GameOverEvent
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.components.DaggerAppComponent
import de.bitb.spacerace.injection.modules.ApplicationModule
import de.bitb.spacerace.injection.modules.DatabaseModule
import de.bitb.spacerace.model.space.maps.MapCreator
import de.bitb.spacerace.model.space.maps.initDefaultMap
import de.bitb.spacerace.ui.screens.GameOverScreen
import de.bitb.spacerace.ui.screens.start.StartScreen
import de.bitb.spacerace.usecase.ui.CommandUsecase
import io.objectbox.BoxStore
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

open class MainGame(
        version: String = "007",
        protected val objBox: BoxStore? = null
) : BaseGame() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    @Inject
    protected lateinit var commandUsecase: CommandUsecase

    @Inject
    protected lateinit var mapDataSource: MapDataSource

    init {
        VERSION = version
    }

    open fun initComponent(): AppComponent =
            DaggerAppComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .databaseModule(DatabaseModule(objBox))
                    .build()

    override fun initGame() {
        EventBus.getDefault().register(this)
        appComponent = initComponent()
        appComponent.inject(this)

        initDefaultMaps()

        commandUsecase.observeStream()
    }

    private fun initDefaultMaps() {
        val maps = MapCreator.values()
                .map { it.createMap().initDefaultMap(it.name) }
                .toMutableList()
        maps.add(createMap())
        mapDataSource.insertMaps(*maps.toTypedArray())
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
        when {
            isCloseGameTipped() -> Gdx.app.exit()
            isBackTipped() -> {
                (screen as BaseScreen)
                        .previousScreen
                        ?.also { changeScreen(it) }
                        ?: Gdx.app.exit()
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
}