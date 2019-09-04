package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.SELECTED_MAP
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.maps.MapCreator
import javax.inject.Inject

class SelectMapCommand(
        private val mapCreator: MapCreator
) : BaseCommand() {

    @Inject
    protected lateinit var fieldController: FieldController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        SELECTED_MAP = mapCreator
    }

}