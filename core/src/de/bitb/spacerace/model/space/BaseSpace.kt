package de.bitb.spacerace.model.space

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.model.Ship
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.enums.Phase.*


abstract class BaseSpace {
    var phase: Phase = MAIN1
    private var firstShip: Ship = Ship(GameColors.NONE)
    var currentShip: Ship = firstShip
        get() = ships[ships.size - 1]
    var ships: MutableList<Ship> = ArrayList()

    val fieldGroups: MutableList<SpaceGroup> = ArrayList()
    val fields: MutableList<SpaceField> = ArrayList()
    val connections: MutableList<SpaceConnection> = ArrayList()

    var diced: Boolean = false
    var diceResult: Int = 0
    val steps: MutableList<SpaceField> = ArrayList()
    var previousStep: SpaceField = SpaceField()
        get() = steps[steps.size - 2]


    init {
        createSpace()
    }

    abstract fun createSpace()

    fun addShip(spaceField1: SpaceField, color: GameColors) {
        val ship = Ship(color)
        ship.fieldPosition = spaceField1
        ship.setPosition(spaceField1.x + spaceField1.width / 2 - ship.width / 2, spaceField1.y + spaceField1.height / 2 - ship.height / 2)
        ship.color = color.color
        ships.add(ship)
        firstShip = ship
    }

    fun addField(spaceField: SpaceField, posX: Float = spaceField.x, posY: Float = spaceField.y) {
        spaceField.setPosition(posX, posY)
        spaceField.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                moveTo(spaceField)
                return true
            }
        })
        fields.add(spaceField)
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
        val connection: SpaceConnection = SpaceConnection(this, spaceField1, spaceField2)
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

    fun moveTo(spaceField: SpaceField) {
        val ship = currentShip
        if (hasConnectionTo(ship.fieldPosition, spaceField) && phase == MOVE) {
            val sameField = steps.size > 1 && steps[steps.size - 2] == spaceField
            if (steps.size <= diceResult || sameField) {
                if (sameField) {
                    steps.removeAt(steps.size - 1)
                } else {
                    steps.add(spaceField)
                }

                ship.fieldPosition = spaceField
                ship.moveTo(spaceField)
                Logger.println("Ship Field: ${ship.fieldPosition.id}, ${ship.fieldPosition.fieldType.name}")

            }
        }
    }

    fun isNextTurn(): Boolean {
        return phase.isNextTurn()
    }

    fun dice(maxResult: Int = 6, anyway: Boolean = false) {
        if (anyway || diceResult - steps.size <= 0 && !diced && phase == MAIN1) {
            diced = if (anyway) diced else true
            steps.clear()
            steps.add(currentShip.fieldPosition)
            diceResult = (Math.random() * maxResult).toInt() + 1
            Logger.println("DiceResult: $diceResult")
        }
    }

    fun stepsLeft(): Int {
        return diceResult - (steps.size - 1)
    }


    fun nextPhase() {
        val allowed = when (phase) {
            MAIN1 -> endMain1()
            MOVE -> endMove()
            MAIN2 -> endMain2()
            NEXT_TURN -> true
        }
        if (allowed) {
            phase = Phase.next(phase)
            if (phase == NEXT_TURN && firstShip != currentShip) {
                phase = MAIN1
            }

            when (phase) {
                MAIN1 -> startMain1()
                MOVE -> startMove()
                MAIN2 -> startMain2()
                NEXT_TURN -> startNextTurn()
            }
        }
        Logger.println("Phase: ${phase.name}")
    }

    private fun startNextTurn() {

    }

    private fun startMain1() {
    }

    private fun startMove() {

    }

    private fun startMain2() {
        val ship = currentShip
        when (ship.fieldPosition.fieldType) {
            FieldType.WIN -> ship.addRandomWin()
            FieldType.LOSE -> ship.substractRandomWin()
            FieldType.SHOP -> openShop()
            FieldType.GIFT -> ship.addRandomGift()
            FieldType.AMBUSH -> Logger.println("AMBUSH ACTION")
            FieldType.MINE -> activateMine(ship)
            FieldType.RANDOM -> Logger.println("RANDOM ACTION")
            FieldType.UNKNOWN -> Logger.println("UNKNOWN ACTION")
        }
    }

    private fun activateMine(ship: Ship) {
        val mine: Mine = ship.fieldPosition as Mine
        mine.setOwner(ship)
    }

    private fun openShop() {
        Logger.println("Open shop")
    }

    private fun endMain1(): Boolean {
        return diced
    }

    private fun endMove(): Boolean {
        return stepsLeft() == 0
    }

    private fun endMain2(): Boolean {
        if (stepsLeft() == 0) {
            val oldShip = ships[0]

            var indexOld = oldShip.zIndex + 1
            for (ship in ships) {
                ship.zIndex = indexOld--
            }

            ships.add(oldShip)
            ships.removeAt(0)

            diced = false
            steps.clear()
            diceResult = 0
            return true
        }
        return false
    }

}

