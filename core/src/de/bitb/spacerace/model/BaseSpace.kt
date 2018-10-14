package de.bitb.spacerace.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.enums.Phase.*


class BaseSpace {
    var phase: Phase = MAIN1
    var ships = ArrayList<Ship>()
    val fields: MutableList<SpaceField> = ArrayList()

    var currentShipIndex: Int = 0
    var diced: Boolean = false
    var diceResult: Int = 0
    val steps: MutableList<SpaceField> = ArrayList()

    init {
        createSpace()
    }

    private fun createSpace() {
        val screenWidth = Gdx.graphics.width
        val screenHeight = Gdx.graphics.height

        val spaceField1 = SpaceField(1, FieldType.GIFT)
        addField(spaceField1)
        val spaceField2 = SpaceField(2, FieldType.MINE)
        addField(spaceField2, screenWidth - spaceField2.width)
        val spaceField3 = SpaceField(3, FieldType.LOSE)
        addField(spaceField3, screenWidth / 2 - spaceField3.width / 2)
        val spaceField4 = SpaceField(4, FieldType.MINE)
        addField(spaceField4, screenWidth - spaceField4.width, screenHeight - spaceField4.height)
        val spaceField5 = SpaceField(5, FieldType.WIN)
        addField(spaceField5, screenWidth / 2 - spaceField5.width / 2, screenHeight - spaceField5.height)
        val spaceField6 = SpaceField(6, FieldType.LOSE)
        addField(spaceField6, posY = screenHeight - spaceField6.height)
        val spaceField7 = SpaceField(7, FieldType.SHOP)
        addField(spaceField7, ((screenWidth / 3 - spaceField7.width * 2 / 3).toFloat()), (screenHeight / 2 - spaceField7.height / 2))
        val spaceField8 = SpaceField(8, FieldType.RANDOM)
        addField(spaceField8, ((screenWidth * 2 / 3 + spaceField8.width / 3).toFloat()), (screenHeight / 2 - spaceField8.height / 2))

        fields.add(spaceField1)
        fields.add(spaceField2)
        fields.add(spaceField3)
        fields.add(spaceField4)
        fields.add(spaceField5)
        fields.add(spaceField6)
        fields.add(spaceField7)
        fields.add(spaceField8)

        addConnection(spaceField1, spaceField3)
        addConnection(spaceField2, spaceField3)
        addConnection(spaceField5, spaceField6)
        addConnection(spaceField5, spaceField4)
        addConnection(spaceField8, spaceField4)
        addConnection(spaceField2, spaceField4)
        addConnection(spaceField3, spaceField7)
        addConnection(spaceField8, spaceField7)
        addConnection(spaceField1, spaceField6)
        addConnection(spaceField7, spaceField6)

        addShip(spaceField1, GameColors.GREEN)
        addShip(spaceField1, GameColors.RED)
        addShip(spaceField1, GameColors.YELLOW)
    }

    private fun addShip(spaceField1: SpaceField, color: GameColors) {
        val ship = Ship(color)
        ship.fieldPosition = spaceField1
        ship.setPosition(spaceField1.x + spaceField1.width / 2 - ship.width / 2, spaceField1.y + spaceField1.height / 2 - ship.height / 2)
        ship.color = color.color
        ships.add(ship)
    }

    private fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        spaceField1.connections.add(spaceField2)
        spaceField2.connections.add(spaceField1)
    }

    private fun addField(spaceField: SpaceField, posX: Float = 0F, posY: Float = 0f) {
        spaceField.setPosition(posX, posY)
        spaceField.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                moveTo(spaceField)
                return true
            }
        })
    }

    fun moveTo(spaceField: SpaceField) {
        val ship = getCurrentShip()
        if (ship.fieldPosition.hasConnectionTo(spaceField) && phase == MOVE) {
            val sameField = steps.size > 1 && steps[steps.size - 2].id == spaceField.id
            if (steps.size < diceResult || sameField) {
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
        return ships[currentShipIndex]
    }

    fun dice(maxResult: Int = 6, anyway: Boolean = false) {
        if (anyway || diceResult - steps.size <= 0 && !diced && phase == MAIN1) {
            diced = if (anyway) diced else true
            steps.clear()
            steps.add(getCurrentShip().fieldPosition)
            diceResult = (Math.random() * maxResult).toInt() + 2
            Logger.println("DiceResult: $diceResult")
        }
    }

    fun stepsLeft(): Int {
        return diceResult - steps.size
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
    }

    private fun endMain1(): Boolean {
        return diced
    }

    private fun endMove(): Boolean {
        return true
    }

    private fun endMain2(): Boolean {
        if (stepsLeft() == 0) {
            currentShipIndex = (currentShipIndex + 1) % ships.size
            diced = false
            steps.clear()
            diceResult = 0
            return true
        }
        return false
    }
}

