package de.bitb.spacerace.config.strings

import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.enums.Language

object GameStrings {

    var PHASE_MAIN1: String = ""
        get() = when (LANGUAGE) {
            Language.ENGLISH -> "Main 1"
            Language.GERMAN -> "Main 1"
        }

    var PHASE_MOVE: String = ""
        get() = when (LANGUAGE) {
            Language.ENGLISH -> "Move"
            Language.GERMAN -> "Bewegen"
        }

    var PHASE_MAIN2: String = ""
        get() = when (LANGUAGE) {
            Language.ENGLISH -> "Main 2"
            Language.GERMAN -> "Main 2"
        }

    var PHASE_END_TURN: String = ""
        get() = when (LANGUAGE) {
            Language.ENGLISH -> "End turn"
            Language.GERMAN -> "Zug Ende"
        }

    var PHASE_END_ROUND: String = ""
        get() = when (LANGUAGE) {
            Language.ENGLISH -> "End round"
            Language.GERMAN -> "Runden Ende"
        }

    object ItemStrings {

        var ITEM_EXTRA_FUEL_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "Some extra fuel.\nUse it to travel more distance."
                Language.GERMAN -> "Etwas extra Treibstoff.\nBenutz es um weitere Strecken zu reisen."
            }

        var ITEM_SPECIAL_FUEL_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "Special fuel to boost your travel distance."
                Language.GERMAN -> "Spezial Treibstoff um weiter reisen zu können."
            }

        var ITEM_SPEED_BOOST_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "Speedboost, roll twice."
                Language.GERMAN -> "Geschwindigkeit erhöhen, würfel 2x"
            }

        var ITEM_CLEAN_DROID_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "A droid to clean up your mess"
                Language.GERMAN -> "Ein Druide der dein Mist aufräumt"
            }

        var ITEM_ION_ENGINE_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "An ion engine,\nits better than the normal drive, i guess."
                Language.GERMAN -> "Ein Ionenantrieb,\nist besser als ein normaler Antrieb, vermutlich."
            }

        var ITEM_SLOW_MINE_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "An slow minePlanet,\nits slowing your enemy"
                Language.GERMAN -> "Eine Verlangsamungsmine,\nsie verlangsamt deine Gegner."
            }

        var ITEM_MOVING_MINE_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "An slow minePlanet,\nits slowing your enemy and it moves!"
                Language.GERMAN -> "Eine Verlangsamungsmine,\nsie verlangsamt deine Gegner und bewegt sich!"
            }
    }

}