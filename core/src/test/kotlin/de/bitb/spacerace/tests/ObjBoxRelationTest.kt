package de.bitb.spacerace.tests

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.game.TestGame
import de.bitb.spacerace.model.items.ItemInfo
import io.objectbox.Box
import junit.framework.Assert.*
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class ObjBoxRelationTest : GameTest() {

    private lateinit var game: TestGame

    @Inject
    protected lateinit var mapBox: Box<SaveData>
    @Inject
    protected lateinit var fieldBox: Box<FieldData>
    @Inject
    protected lateinit var playerBox: Box<PlayerData>
    @Inject
    protected lateinit var itemBox: Box<ItemData>

    protected lateinit var players: List<PlayerData>
    protected lateinit var fields: List<FieldData>
    protected lateinit var goal: List<FieldData>

    @Before
    override fun setup() {
        super.setup()
        game = TestGame()
        game.initGame()
        TestGame.testComponent.inject(this)
    }

    @Test
    fun addEntity_objUuidUpdated() {
        val saveData = SaveData()

        assertThat(saveData.uuid, `is`(0L))
        mapBox.put(saveData)
        assertThat(saveData.uuid, `is`(not(0L)))
    }

    @Test
    fun addEntity_dbAndObjNotSame_justEquals() {
        val saveData = SaveData()
        mapBox.put(saveData)
        val dbSaveData = mapBox.all.first()

        assertFalse(saveData === dbSaveData)
        assertTrue(saveData == dbSaveData)
    }

    @Test
    fun oneToManyTest_playerDataPositionField_addField_savePlayerData_playerNotOnField_playerOnDBField() {
        val fieldData = FieldData()
        val playerData = PlayerData().apply { positionField.target = fieldData }

        //player not on field
        assertFalse(fieldData.players.any { it == playerData })

        playerBox.put(playerData)
        //player after save still not on field
        assertFalse(fieldData.players.any { it == playerData })

        //player on db field
        val dbFieldData = fieldBox.get(fieldData.uuid)
        assertEquals(fieldData, dbFieldData)
        assertTrue(dbFieldData.players.any { it == playerData })
    }

    @Test
    fun manyToOneTest_fieldDataPlayers_addPlayer_saveFieldData_playerPositionFieldSet() {
        val playerData = PlayerData()
        val fieldData = FieldData().apply { players.add(playerData) }

        //player not on field
        assertTrue(playerData.positionField.isNull)

        fieldBox.put(fieldData)
        //field saved, player on field, same objs :D
        assertTrue(playerData.positionField.target === fieldData)

        //DBPlayer on field
        val dbPlayerData = playerBox.get(playerData.uuid)
        assertEquals(dbPlayerData.positionField.target, fieldData)

        assertEquals(playerData, dbPlayerData)

    }

    @Test //RESULT: save Both
    fun oneToManyTest_addPlayerToField1_saveFields_playerOnField1_addPlayerToField2_playerOnBothFields_saveField2_stillBoth_saveField1_stillBoth_dbField1NoPlayer() {
        val playerData = PlayerData()
        val fieldData1 = FieldData().apply { players.add(playerData) }
        val fieldData2 = FieldData()

        //player not on field
        assertTrue(playerData.positionField.isNull)

        fieldBox.put(fieldData1, fieldData2)
        //fields saved, player on field1, same objs :D
        assertTrue(playerData.positionField.target === fieldData1)
        assertFalse(playerData.positionField.target === fieldData2)

        //player on field1 not field2
        assertTrue(fieldData1.players.any { it == playerData })
        assertFalse(fieldData2.players.any { it == playerData })

        //add player to field2
        fieldData2.apply { players.add(playerData) }

        //player still on field1, same objs :D
        assertTrue(playerData.positionField.target === fieldData1)
        assertFalse(playerData.positionField.target === fieldData2)

        //player on both fields
        assertTrue(fieldData1.players.any { it == playerData })
        assertTrue(fieldData2.players.any { it == playerData })

        //save Field2
        fieldBox.put(fieldData2)

        //player on field2 not on field1
        assertFalse(playerData.positionField.target == fieldData1)
        assertTrue(playerData.positionField.target === fieldData2)

        //player still on both fields
        assertTrue(fieldData1.players.any { it == playerData })
        assertTrue(fieldData2.players.any { it == playerData })

        //save Field1
        fieldBox.put(fieldData1)
        //player still on both fields
        assertTrue(fieldData1.players.any { it == playerData })
        assertTrue(fieldData2.players.any { it == playerData })

        val dbFieldData1 = fieldBox.get(fieldData1.uuid)
        //player on field2 not on dbField1
        assertFalse(dbFieldData1.players.any { it == playerData })
        assertTrue(fieldData2.players.any { it == playerData })
    }

    @Test //RESULT:
    fun oneToManyTest_addPlayerToField1_saveFields_playerOnField1_addPlayerToField2_playerOnBothFields_saveField2_stillBoth_dbField1NoPlayer() {
        val playerData = PlayerData()
        val fieldData1 = FieldData().apply { players.add(playerData) }
        val fieldData2 = FieldData()

        //player not on field
        assertTrue(playerData.positionField.isNull)

        fieldBox.put(fieldData1, fieldData2)
        //fields saved, player on field1, same objs :D
        assertTrue(playerData.positionField.target === fieldData1)
        assertFalse(playerData.positionField.target === fieldData2)

        //player on field1 not field2
        assertTrue(fieldData1.players.any { it == playerData })
        assertFalse(fieldData2.players.any { it == playerData })

        //add player to field2
        fieldData2.apply { players.add(playerData) }

        //player still on field1, same objs :D
        assertTrue(playerData.positionField.target === fieldData1)
        assertFalse(playerData.positionField.target === fieldData2)

        //player on both fields
        assertTrue(fieldData1.players.any { it == playerData })
        assertTrue(fieldData2.players.any { it == playerData })

        //save Field2
        fieldBox.put(fieldData2)

        //player on field2 not on field1
        assertFalse(playerData.positionField.target == fieldData1)
        assertTrue(playerData.positionField.target === fieldData2)

        //player still on both fields
        assertTrue(fieldData1.players.any { it == playerData })
        assertTrue(fieldData2.players.any { it == playerData })

        val dbFieldData1 = fieldBox.get(fieldData1.uuid)
        //player on field2 not on dbField1
        assertFalse(dbFieldData1.players.any { it == playerData })
        assertTrue(fieldData2.players.any { it == playerData })
    }

    @Test //RESULT:
    fun manyToManyTest() {
        val playerData = PlayerData()
        val fieldData = FieldData().apply { players.add(playerData) }

        fieldBox.put(fieldData)
        val dbFieldData = fieldBox.get(fieldData.uuid)
        val dbPlayerData = playerBox.get(playerData.uuid)

        assertTrue(dbFieldData.players.any { it == dbPlayerData })

        val fieldPlayerStep1 = dbFieldData.players.find { it.uuid == dbPlayerData.uuid }!!
        val fieldPlayerFieldStep1 = fieldPlayerStep1.positionField.target!!
        val fieldPlayerStep2 = fieldPlayerFieldStep1.players.find { it.uuid == dbPlayerData.uuid }!!
        val fieldPlayerFieldStep2 = fieldPlayerStep2.positionField.target!!
        assertTrue(fieldPlayerFieldStep1 == dbFieldData)
        assertTrue(fieldPlayerStep1 == dbPlayerData)
        assertTrue(fieldPlayerFieldStep2 == dbFieldData)
        assertTrue(fieldPlayerStep2 == dbPlayerData)

        assertTrue(fieldPlayerStep1.positionField.target!! == dbPlayerData.positionField.target!!)
        assertTrue(fieldPlayerStep2.positionField.target!! == dbPlayerData.positionField.target!!)

        assertTrue(fieldPlayerFieldStep1.players.any { it == dbPlayerData })
        assertTrue(fieldPlayerFieldStep2.players.any { it == dbPlayerData })
        assertTrue(dbFieldData.players.any { it == dbPlayerData })

        dbFieldData.players.clear()

        assertTrue(fieldPlayerStep1.positionField.target!! == dbPlayerData.positionField.target!!)
        assertTrue(fieldPlayerStep2.positionField.target!! == dbPlayerData.positionField.target!!)

        assertTrue(fieldPlayerFieldStep1.players.any { it == dbPlayerData })
        assertTrue(fieldPlayerFieldStep2.players.any { it == dbPlayerData })
        assertTrue(dbFieldData.players.none { it == dbPlayerData })

        fieldBox.put(dbFieldData)

        assertTrue(fieldPlayerFieldStep1.players.any { it == dbPlayerData })
        assertTrue(fieldPlayerFieldStep2.players.any { it == dbPlayerData })
        assertTrue(dbFieldData.players.none { it == dbPlayerData })
    }

    @Test
    fun delete_item_check_toMany() {
        val itemData = ItemData(itemInfo = ItemInfo.EXTRA_FUEL())
        val playerData = PlayerData().apply {
            storageItems.add(itemData)
            equippedItems.add(itemData)
        }
        playerBox.put(playerData)
        val dbItem = itemBox.get(itemData.id)
        var dbPlayer = playerBox.get(playerData.uuid)

        assertTrue(dbPlayer.storageItems.any { it.id == dbItem.id })
        assertTrue(dbPlayer.equippedItems.any { it.id == dbItem.id })

        itemBox.remove(dbItem)

        dbPlayer = playerBox.get(playerData.uuid)
        assertTrue(dbPlayer.storageItems.none { it.id == dbItem.id })
        assertTrue(dbPlayer.equippedItems.none { it.id == dbItem.id })

    }
}