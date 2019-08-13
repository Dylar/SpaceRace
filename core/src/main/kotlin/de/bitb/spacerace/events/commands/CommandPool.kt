package de.bitb.spacerace.events.commands

import de.bitb.spacerace.events.commands.start.ChangeDiceAmountCommand

object CommandPool {
    val pool = mutableMapOf<String, BaseCommand>()

    fun getCommand(commandType: String) {
        when (commandType) {
            ChangeDiceAmountCommand::class.toString() -> {
            }
            else -> {
            }
        }
    }

    fun addPool(baseCommand: BaseCommand) {
    }


}
//
//sealed class Command() {
//
//    open fun reset() {
//
//    }
//}
//
//sealed class ChangeDiceAmountCommand() : Command() {
//}
//
//sealed class DeliveriesOnDeviceError : Command()
//sealed class PrintDeliveryError : Command()
//sealed class CannotDeleteFinsishedDelivery : Command()
