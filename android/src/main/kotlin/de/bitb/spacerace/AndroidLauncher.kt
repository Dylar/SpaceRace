package de.bitb.spacerace

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import de.bitb.spacerace.DatabaseProvider.createNewObjectbox
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.utils.Logger


class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.androidLogger = {
            Log.e("ANDROID:", it)
        }

        val version = try {
            context!!.packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            "00ERROR"
        }

        val config = AndroidApplicationConfiguration()
        initialize(MainGame(version, createNewObjectbox(this)), config)
    }

}
