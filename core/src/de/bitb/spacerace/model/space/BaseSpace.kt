package de.bitb.spacerace.model.space

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.model.player.Ship
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.enums.Phase.*
import de.bitb.spacerace.model.player.history.*


abstract class BaseSpace {
    val history: History = History()

    var phase: Phase = MAIN1
    private var firstShip: Ship = Ship(GameColors.NONE)
    var currentShip: Ship = firstShip
        get() = ships[ships.size - 1]
    var ships: MutableList<Ship> = ArrayList()

    val fieldGroups: MutableList<SpaceGroup> = ArrayList()
    val fields: MutableList<SpaceField> = ArrayList()
    val fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = HashMap()
    val connections: MutableList<SpaceConnection> = ArrayList()

    var diced: Boolean = false
    var diceResult: Int = 0
    var steps: MutableList<SpaceField> = ArrayList()
    var previousStep: SpaceField = SpaceField()
        get() = steps[steps.size - 2]

    init {
        createSpace()
        history.nextRound(currentShip)
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
            val sameField = steps.size > 1 && previousStep == spaceField
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
            steps.add(currentShip.fieldPosition)
            diceResult += (Math.random() * maxResult).toInt() + 1
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
            START_ROUND -> true
        }
        if (allowed) {
            phase = Phase.next(phase)
            if (phase == START_ROUND && firstShip != currentShip) {
                phase = MAIN1
            }

            when (phase) {
                MAIN1 -> startMain1()
                MOVE -> startMove()
                MAIN2 -> startMain2()
                START_ROUND -> startEndRound()
            }
        }
        Logger.println("Phase: ${phase.name}")
    }

    private fun startEndRound() {
        val list: MutableList<SpaceField> = fieldsMap[FieldType.MINE]!!
        for (spaceField in list) {
           val harvest = (spaceField as MineField).harvestOres()
            history.addRoundActivity(HarvestOres(harvest))
        }

        history.nextRound(currentShip)
    }

    private fun startMain1() {
        history.nextPlayer(currentShip)
    }

    private fun startMove() {

    }

    private fun startMain2() {
        val ship = currentShip
        when (ship.fieldPosition.fieldType) {
            FieldType.WIN ->{
                val lose = ship.addRandomWin()
                history.addActivity(ChangeCredits(lose))
            }
            FieldType.LOSE ->{
                val lose = ship.substractRandomWin()
                history.addActivity(ChangeCredits(lose))
            }
            FieldType.GIFT -> {
                val item = ship.addRandomGift()
                history.addActivity(AddItem(item))
            }
            FieldType.MINE ->{
                activateMine(ship)
                history.addActivity(OccupyMine(ship.fieldPosition as MineField))
            }
            FieldType.SHOP -> openShop()
            FieldType.RANDOM -> Logger.println("RANDOM ACTION")
            FieldType.UNKNOWN -> Logger.println("UNKNOWN ACTION")
            FieldType.AMBUSH -> Logger.println("AMBUSH ACTION")
        }
    }

    private fun activateMine(ship: Ship) {
        val mineField: MineField = ship.fieldPosition as MineField
        mineField.setOwner(ship)
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


            history.setSteps(steps)
            steps = ArrayList()

            diceResult = 0
            diced = false
            return true
        }
        return false
    }

}

