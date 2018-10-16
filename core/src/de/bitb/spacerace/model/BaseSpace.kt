package de.bitb.spacerace.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.enums.Phase.*


abstract class BaseSpace {
    var phase: Phase = MAIN1
    var ships = ArrayList<Ship>()
    val fields: MutableList<SpaceField> = ArrayList()

    var diced: Boolean = false
    var diceResult: Int = 0
    val steps: MutableList<SpaceField> = ArrayList()

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
    }

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        spaceField1.connections.add(spaceField2)
        spaceField2.connections.add(spaceField1)
    }

    fun addField(spaceField: SpaceField, posX: Float = 0F, posY: Float = 0f) {
        spaceField.setPosition(posX, posY)
        spaceField.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                moveTo(spaceField)
                return true
            }
        })
        fields.add(spaceField)
    }

    fun moveTo(spaceField: SpaceField) {
        val ship = getCurrentShip()
        if (ship.fieldPosition.hasConnectionTo(spaceField) && phase == MOVE) {
            val sameField = steps.size > 1 && steps[steps.size - 2].id == spaceField.id
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

    fun getCurrentShip(): Ship {
        return ships[ships.size - 1]
    }

    fun dice(maxResult: Int = 6, anyway: Boolean = false) {
        if (anyway || diceResult - steps.size <= 0 && !diced && phase == MAIN1) {
            diced = if (anyway) diced else true
            steps.clear()
            steps.add(getCurrentShip().fieldPosition)
            diceResult = (Math.random() * maxResult).toInt() + 1
            Logger.println("DiceResult: $diceResult")
        }
    }

    fun stepsLeft(): Int {
        return diceResult - (steps.size - 1)
    }

    fun getPreviousStep(): SpaceField {
        return steps[steps.size - 2]
    }

    fun nextPhase() {
        val allowed = when (phase) {
            MAIN1 -> endMain1()
            MOVE -> endMove()
            MAIN2 -> endMain2()
        }
        if (allowed) {
            phase = Phase.next(phase)

            when (phase) {
                MAIN1 -> startMain1()
                MOVE -> startMove()
                MAIN2 -> startMain2()
            }
        }
        Logger.println("Phase: ${phase.name}")
    }

    private fun startMain1() {
    }

    private fun startMove() {

    }

    private fun startMain2() {
        val ship = getCurrentShip()
        when (ship.fieldPosition.fieldType) {
            FieldType.WIN -> ship.addRandomWin()
            FieldType.LOSE -> ship.substractRandomWin()
            FieldType.SHOP -> openShop()
            FieldType.GIFT -> ship.addRandomGift()
            FieldType.GIFT -> ship.addRandomGift()
            FieldType.MINE -> Logger.println("MINE ACTION")
            FieldType.RANDOM -> Logger.println("RANDOM ACTION")
            FieldType.UNKNOWN -> Logger.println("UNKNOWN ACTION")
        }
    }

    private fun openShop() {
        Logger.println("Open shop")
    }

    private fun endMain1(): Boolean {
        return diced
    }

    private fun endMove(): Boolean {
        return true
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

