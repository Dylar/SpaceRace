package de.bitb.spacerace.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.injection.components.AppComponent
import de.bitb.spacerace.injection.components.DaggerAppComponent
import de.bitb.spacerace.injection.modules.ApplicationModule
import de.bitb.spacerace.injection.modules.DatabaseModule
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.fields.FieldData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.ui.screens.start.StartScreen
import io.objectbox.Box
import javax.inject.Inject

class MainGame : BaseGame() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    @Inject
    lateinit var inputHandler: InputHandler

    @Inject
    lateinit var box: Box<FieldData>

    lateinit var gameController: GameController

    override fun initScreen() {
        appComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .databaseModule(DatabaseModule())
                .build()
        appComponent.inject(this)
        setScreen(StartScreen(this))

//        box.put(FieldData(fieldType = FieldType.MINE))
//        box.put(FieldData(fieldType = FieldType.MINE, owner = PlayerColor.NAVY))
        val all = box.all
        Logger.log("RESULT: ", all)
//        setScreen(GameScreen(this))
//        setScreen(GameOverScreen(this))
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

    fun clear() {
        gameController.compositeDisposable.clear()
        (screen as BaseScreen).clear()
    }

}