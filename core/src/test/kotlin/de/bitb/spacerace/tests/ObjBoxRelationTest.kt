package de.bitb.spacerace.tests

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.game.TestGame
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

}