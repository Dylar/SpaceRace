package de.bitb.spacerace

import android.content.Context
import de.bitb.spacerace.model.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder
import java.io.File

object DatabaseProvider {

    fun createNewObjectbox(context: Context): BoxStore {
//        BoxStore.deleteAllFiles(File(BoxStoreBuilder.DEFAULT_NAME))
        return MyObjectBox.builder().androidContext(context).build()
    }
}