package de.bitb.spacerace.controller

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.player.MoveCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.disposable.moving.MovingItem
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.ConnectionList
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.model.space.maps.SpaceMap

class FieldController(playerController: PlayerController) : DefaultFunction {

    val fieldGroups: MutableList<SpaceGroup> = ArrayList()
    val fields: MutableList<SpaceField> = ArrayList()
    val fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = HashMap()
    val connections: ConnectionList = ConnectionList(playerController)

    init {
        FieldType.values().forEach { field -> fieldsMap[field] = ArrayList() }
    }

    fun getField(positionData: PositionData): SpaceField {
        fields.forEach { if (it.positionData.isPosition(positionData)) return it }
        return SpaceField.NONE
    }

    fun getField(item: Item): SpaceField {
        fields.forEach { if (it.disposedItems.contains(item)) return it }
        return SpaceField.NONE
    }

    fun addShip(player: Player, spaceField1: SpaceField) {
        val playerPosition = player.positionData
        val fieldPosition = spaceField1.positionData
        player.setPosition(fieldPosition.posX, fieldPosition.posY)
        player.getGameImage().x += playerPosition.height / 2
        player.getGameImage().y += playerPosition.width / 2
        player.getGameImage().color = player.playerData.playerColor.color
    }

    fun addField(gameController: GameController, spaceField: SpaceField) {
        spaceField.getGameImage().addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                gameController.inputHandler.handleCommand(MoveCommand(spaceField, gameController.playerController.currentPlayer.playerData.playerColor))
                return true
            }
        })
        fields.add(spaceField)
        addFieldMap(spaceField)
    }

    private fun addFieldMap(spaceField: SpaceField) {
        fieldsMap[spaceField.fieldType]!!.add(spaceField)
    }

    fun initMap(gameController: GameController, map: SpaceMap) {
        addFields(gameController, *map.groups.toTypedArray())
    }

    fun addFields(gameController: GameController, vararg spaceGroups: SpaceGroup) {
        for (spaceGroup in spaceGroups) {
            fieldGroups.add(spaceGroup)
            for (field in spaceGroup.fields.entries.withIndex()) {
                addField(gameController, field.value.value)
            }
        }
    }

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        val connection = SpaceConnection(spaceField1, spaceField2)
        connections.add(connection)
        spaceField1.connections.add(connection)
        spaceField2.connections.add(connection)
    }

    fun harvestOres(game: MainGame) {
        val list: MutableList<SpaceField> = fieldsMap[FieldType.MINE]!!
        for (spaceField in list) {
            val harvest = (spaceField as MineField).harvestOres(game)
//            gameController.history.addRoundActivity(HarvestOres(harvest))//TODO mach das in den command
        }

    }

    fun getRandomTunnel(game: MainGame, playerColor: PlayerColor): SpaceField {
        val playerPosition = getPlayerPosition(game, playerColor)
        val tunnelList = fieldsMap[FieldType.TUNNEL]!!
        var tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]

        while (tunnel.positionData.isPosition(playerPosition)) {
            tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]
        }

        return tunnel
    }

    fun moveMovables(game: MainGame) {
        val moveItem = { item: MovingItem, toRemove: MutableList<Item> ->
            val field = getItemField(game, item)
            val list = field.connections
            val con = list[(Math.random() * list.size).toInt()]
            val newField = con.getOpposite(field)
            newField.disposedItems.add(item)

            val itemImage = item.getItemImage()
            itemImage.moveTo(item, newField.positionData.withCenter())
            toRemove.add(item)
        }

        val fieldList: MutableList<SpaceField> = ArrayList()
        fields.forEach {
            if (!it.disposedItems.isEmpty()) {
                fieldList.add(it)
            }
        }
        fieldList.forEach { field: SpaceField ->
            val toRemove: MutableList<Item> = ArrayList()
            field.disposedItems.forEach { it ->
                if (it is MovingItem && it.getGameImage().isIdling()) {
                    moveItem(it, toRemove)
                }
            }
            field.disposedItems.removeAll(toRemove)
        }

    }

}