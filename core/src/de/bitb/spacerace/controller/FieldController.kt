package de.bitb.spacerace.controller

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.events.commands.player.MoveCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.ConnectionList
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.model.space.maps.SpaceMap

class FieldController(playerController: PlayerController) {

    val fieldGroups: MutableList<SpaceGroup> = ArrayList()
    val fields: MutableList<SpaceField> = ArrayList()
    val fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = HashMap()
    val connections: ConnectionList = ConnectionList(playerController)

    init {
        FieldType.values().forEach { field -> fieldsMap[field] = ArrayList() }
    }

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
        fieldsMap[spaceField.fieldType]!!.add(spaceField)
    }

    fun initMap(inputHandler: InputHandler, map: SpaceMap) {
        addFields(inputHandler, *map.groups.toTypedArray())
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
//            gameController.history.addRoundActivity(HarvestOres(harvest))//TODO mach das in den command
        }

    }

    fun occupyMine(player: Player) {
        val mineField: MineField = player.playerData.fieldPosition as MineField
        mineField.owner = player
    }

    fun getRandomTunnel(playerColor: PlayerColor): SpaceField {
        val tunnel = fieldsMap[FieldType.TUNNEL]!!
        return tunnel[(Math.random() * tunnel.size).toInt()]
    }

}