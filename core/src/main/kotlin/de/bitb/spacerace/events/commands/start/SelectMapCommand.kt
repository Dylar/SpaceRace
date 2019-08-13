package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.maps.MapCollection
import javax.inject.Inject

class SelectMapCommand(val mapCollection: MapCollection) : BaseCommand() {

    @Inject
    protected lateinit var fieldController: FieldController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        fieldController.spaceMap = mapCollection
    }

}