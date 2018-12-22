package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.events.commands.EndRoundCommand
import de.bitb.spacerace.model.events.commands.EndTurnCommand
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.history.AddItem
import de.bitb.spacerace.model.history.ChangeCredits
import de.bitb.spacerace.model.history.OccupyMine
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.MineField

class PhaseController(val space: BaseSpace, val inputHandler: InputHandler) {

    fun nextPhase() {

        val playerData = space.playerController.currentPlayer.playerData
        playerData.phase = Phase.next(playerData.phase)

        when (playerData.phase) {
            Phase.MAIN1 -> startMain1()
            Phase.MOVE -> startMove()
            Phase.MAIN2 -> startMain2()
            Phase.END_TURN -> startEndTurn()
        }

        Logger.println("Phase: ${playerData.phase.name}")
    }

    private fun startEndTurn() {
        inputHandler.handleCommand(EndTurnCommand())
    }

    private fun startMain1() {
//        space.history.nextPlayer(space.playerController.currentPlayer)
    }

    private fun startMove() {

    }

    private fun startMain2() {
        //TODO ObtainField
        val player = space.playerController.currentPlayer
        when (player.playerData.fieldPosition.fieldType) {
            FieldType.WIN -> {
                val lose = player.addRandomWin()
//                space.history.addActivity(ChangeCredits(lose))
            }
            FieldType.LOSE -> {
                val lose = player.substractRandomWin()
//                space.history.addActivity(ChangeCredits(lose))
            }
            FieldType.GIFT -> {
                val item = player.addRandomGift()
//                space.history.addActivity(AddItem(item))
            }
            FieldType.MINE -> {
                activateMine(player)
//                space.history.addActivity(OccupyMine(player.playerData.fieldPosition as MineField))
            }
            FieldType.SHOP -> openShop()
            FieldType.RANDOM -> Logger.println("RANDOM ACTION")
            FieldType.UNKNOWN -> Logger.println("UNKNOWN ACTION")
            FieldType.AMBUSH -> Logger.println("AMBUSH ACTION")
        }
    }

    private fun activateMine(player: Player) {
        val mineField: MineField = player.playerData.fieldPosition as MineField
        mineField.setOwner(player)
    }

    private fun openShop() {
        Logger.println("Open shop")
    }

    private fun canEndMain1(playerData: PlayerData = space.playerController.currentPlayer.playerData): Boolean {
        return playerData.phase.isMain1() && playerData.diced
    }

    private fun canEndMove(playerData: PlayerData = space.playerController.currentPlayer.playerData): Boolean {
        return playerData.phase.isMoving() && space.playerController.stepsLeft() == 0
    }

    private fun canEndMain2(playerData: PlayerData = space.playerController.currentPlayer.playerData): Boolean {
        return playerData.phase.isMain2()
    }

    fun canContinue(playerData: PlayerData = space.playerController.currentPlayer.playerData): Boolean {
        return when (playerData.phase) {
            Phase.MAIN1 -> canEndMain1()
            Phase.MOVE -> canEndMove()
            Phase.MAIN2 -> canEndMain2()
            Phase.END_TURN -> playerData.phase.isEndTurn()
            Phase.END_ROUND -> playerData.phase.isEndRound()
        }
    }

}