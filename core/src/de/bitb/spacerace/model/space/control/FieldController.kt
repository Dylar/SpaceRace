package de.bitb.spacerace.model.space.control

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.events.commands.MoveCommand
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup

class FieldController() {

    val fieldGroups: MutableList<SpaceGroup> = ArrayList()
    val fields: MutableList<SpaceField> = ArrayList()
    val fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = HashMap()
   lateinit var connections: ConnectionList

    fun addShip(player: Player, spaceField1: SpaceField) {
        player.playerData.fieldPosition = spaceField1
        player.setPosition(spaceField1.x + spaceField1.width / 2 - player.width / 2, spaceField1.y + spaceField1.height / 2 - player.height / 2)
        player.color = player.playerData.playerColor.color
    }

    fun addField(inputHandler: InputHandler, spaceField: SpaceField, posX: Float = spaceField.x, posY: Float = spaceField.y) {
        spaceField.setPosition(posX, posY)
        spaceField.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                inputHandler.handleCommand(MoveCommand(spaceField))
                return true
            }
        })
        fields.add(spaceField)
        addFieldMap(spaceField)
    }

    private fun addFieldMap(spaceField: SpaceField) {
        var list = fieldsMap[spaceField.fieldType]
        if (list == null) {
            list = ArrayList()
            fieldsMap[spaceField.fieldType] = list
        }
        list.add(spaceField)
    }

    fun addFields(inputHandler: InputHandler, vararg spaceGroups: SpaceGroup) {
        for (spaceGroup in spaceGroups) {
            fieldGroups.add(spaceGroup)
            for (field in spaceGroup.fields.entries.withIndex()) {
                addField(inputHandler, field.value.value)
            }
        }
    }

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        val connection: SpaceConnection = SpaceConnection(spaceField1, spaceField2)
        connections.add(connection)
    }

    fun hasConnectionTo(spaceField1: SpaceField, spaceField2: SpaceField): Boolean {
        for (connection in connections) {
            if (connection.isConnection(spaceField1, spaceField2)) {
                return true
            }
        }
        return false
    }

    fun harvestOres() {
        val list: MutableList<SpaceField> = fieldsMap[FieldType.MINE]!!
        for (spaceField in list) {
            val harvest = (spaceField as MineField).harvestOres()
//            gameController.history.addRoundActivity(HarvestOres(harvest))
        }

    }

    fun activateMine(player: Player) {
        val mineField: MineField = player.playerData.fieldPosition as MineField
        mineField.setOwner(player)
    }
}