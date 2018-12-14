package de.bitb.spacerace.config.strings

import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.enums.Language

object GameStrings {

    object ItemStrings{

        var ITEM_EXTRA_FUEL_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "Some extra fuel.\nUse it to travel more distance."
                Language.GERMAN -> "Etwas extra Treibstoff.\nBenutz es um weitere Strecken zu reisen."
            }

        var ITEM_SPEZIAL_FUEL_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "Spezial fuel to boost your travel distance."
                Language.GERMAN -> "Spezial Treibstoff um weiter reisen zu können."
            }

        var ITEM_ION_ENGINE_TEXT: String = ""
            get() = when (LANGUAGE) {
                Language.ENGLISH -> "An ion engine,\nits better than the normal drive, i guess."
                Language.GERMAN -> "Ein Ionenantrieb,\nist besser als ein normaler Antrieb, vermutlich.."
            }
    }

}