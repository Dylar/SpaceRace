package de.bitb.spacerace

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.google.android.play.core.missingsplits.MissingSplitsManagerFactory
import de.bitb.spacerace.DatabaseProvider.createNewObjectbox
import de.bitb.spacerace.core.MainGame

class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        if (MissingSplitsManagerFactory.create(this)
//                        .disableAppIfMissingRequiredSplits()) {
//            return // Skip app initialization.
//        }
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        initialize(MainGame(createNewObjectbox(this)), config)
    }
}
