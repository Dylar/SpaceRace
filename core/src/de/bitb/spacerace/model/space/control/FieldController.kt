package de.bitb.spacerace.model.space.control

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.history.HarvestOres
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup

class FieldController(val space: BaseSpace) {

    val fieldGroups: MutableList<SpaceGroup> = ArrayList()
    val fields: MutableList<SpaceField> = ArrayList()
    val fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = HashMap()
    val connections: MutableList<SpaceConnection> = ArrayList()

    fun addShip(spaceField1: SpaceField, color: PlayerColor) {
        val ship = Player(color)
        ship.fieldPosition = spaceField1
        ship.setPosition(spaceField1.x + spaceField1.width / 2 - ship.width / 2, spaceField1.y + spaceField1.height / 2 - ship.height / 2)
        ship.color = color.color
        space.playerController.players.add(ship)
        space.playerController.firstPlayer = ship
    }

    fun addField(spaceField: SpaceField, posX: Float = spaceField.x, posY: Float = spaceField.y) {
        spaceField.setPosition(posX, posY)
        spaceField.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                space.playerController.moveTo(spaceField)
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

    fun addFields(vararg spaceGroups: SpaceGroup) {
        for (spaceGroup in spaceGroups) {
            fieldGroups.add(spaceGroup)
            for (field in spaceGroup.fields.entries.withIndex()) {
                addField(field.value.value)
            }
        }
    }

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        val connection: SpaceConnection = SpaceConnection(space, spaceField1, spaceField2)
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
            space.history.addRoundActivity(HarvestOres(harvest))
        }

    }
}