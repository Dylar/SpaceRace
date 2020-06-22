package de.bitb.spacerace.core.events.commands.start

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.ui.screens.editor.EditorGloc
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.core.events.commands.CommandPool.getCommand
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.ui.screens.editor.gdx.EditorScreen
import de.bitb.spacerace.usecase.game.init.LoadEditorUsecase
import javax.inject.Inject

class LoadEditorCommand : BaseCommand() {

    companion object {
        fun get(mapName: String = "EDITOR_MAP") =
                getCommand(LoadEditorCommand::class)
                        .also { it.mapName = mapName }
    }

    @Inject
    protected lateinit var loadEditorUsecase: LoadEditorUsecase

    @Inject
    protected lateinit var editorGloc: EditorGloc

    @Inject
    protected lateinit var game: MainGame

    private lateinit var mapName: String

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        loadEditorUsecase.getResult(
                params = mapName,
                onSuccess = ::onSuccess,
                onError = ::onError)
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        reset()
    }

    private fun onSuccess(result: MapData) {
        setGraphics(result)
        reset()
    }

    private fun setGraphics(mapData: MapData) {
        val screen = EditorScreen(game.screen as BaseScreen, mapData)
        editorGloc.initEditor(mapData)
        game.screen = screen
    }

}