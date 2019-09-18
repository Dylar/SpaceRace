package de.bitb.spacerace.controller

import de.bitb.spacerace.config.DICE_MAX
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsController
@Inject constructor(
) {

    fun changeDiceAmount(amount: Int) {
        DICE_MAX += amount
        DICE_MAX = if (DICE_MAX < 1) 1 else DICE_MAX

    }
}