package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.history.AddItem
import de.bitb.spacerace.model.history.ChangeCredits
import de.bitb.spacerace.model.history.OccupyMine
import de.bitb.spacerace.model.space.fields.MineField

class PhaseController(val space: BaseSpace) {

    var phase: Phase = Phase.MAIN1

    fun nextPhase() {
        val allowed = when (phase) {
            Phase.MAIN1 -> endMain1()
            Phase.MOVE -> endMove()
            Phase.MAIN2 -> endMain2()
            Phase.END_ROUND -> true
        }
        if (allowed) {
            phase = Phase.next(phase)
            if (phase == Phase.END_ROUND && space.playerController.isRoundEnd()) {
                phase = Phase.MAIN1
            }

            when (phase) {
                Phase.MAIN1 -> startMain1()
                Phase.MOVE -> startMove()
                Phase.MAIN2 -> startMain2()
                Phase.END_ROUND -> startEndRound()
            }
        }
        Logger.println("Phase: ${phase.name}")
    }

    private fun startEndRound() {
        space.fieldController.harvestOres()
        space.history.nextRound(space.playerController.currentPlayer)
    }

    private fun startMain1() {
        space.history.nextPlayer(space.playerController.currentPlayer)
    }

    private fun startMove() {

    }

    private fun startMain2() {
        val ship = space.playerController.currentPlayer
        when (ship.fieldPosition.fieldType) {
            FieldType.WIN -> {
                val lose = ship.addRandomWin()
                space.history.addActivity(ChangeCredits(lose))
            }
            FieldType.LOSE -> {
                val lose = ship.substractRandomWin()
                space.history.addActivity(ChangeCredits(lose))
            }
            FieldType.GIFT -> {
                val item = ship.addRandomGift()
                space.history.addActivity(AddItem(item))
            }
            FieldType.MINE -> {
                activateMine(ship)
                space.history.addActivity(OccupyMine(ship.fieldPosition as MineField))
            }
            FieldType.SHOP -> openShop()
            FieldType.RANDOM -> Logger.println("RANDOM ACTION")
            FieldType.UNKNOWN -> Logger.println("UNKNOWN ACTION")
            FieldType.AMBUSH -> Logger.println("AMBUSH ACTION")
        }
    }

    private fun activateMine(player: Player) {
        val mineField: MineField = player.fieldPosition as MineField
        mineField.setOwner(player)
    }

    private fun openShop() {
        Logger.println("Open shop")
    }

    private fun endMain1(): Boolean {
        return space.playerController.diced
    }

    private fun endMove(): Boolean {
        return space.playerController.stepsLeft() == 0
    }

    private fun endMain2(): Boolean {
        if (space.playerController.stepsLeft() == 0) {
            val oldShip = space.playerController.players[0]

            var indexOld = oldShip.zIndex + 1
            for (ship in space.playerController.players) {
                ship.zIndex = indexOld--
            }

            space.playerController.players.add(oldShip)
            space.playerController.players.removeAt(0)


            space.history.setSteps(space.playerController.steps)
            space.playerController.steps = ArrayList()

            space.playerController.diceResult = 0
            space.playerController.diced = false
            return true
        }
        return false
    }

}