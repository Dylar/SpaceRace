package de.bitb.spacerace

import android.content.pm.PackageManager
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import de.bitb.spacerace.DatabaseProvider.createNewObjectbox
import de.bitb.spacerace.core.MainGame


class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
