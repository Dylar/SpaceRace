package de.bitb.spacerace

import android.content.Context
import com.getkeepsafe.relinker.ReLinker
import de.bitb.spacerace.model.MyObjectBox
import de.bitb.spacerace.utils.Logger
import io.objectbox.BoxStore

object DatabaseProvider {

    fun createNewObjectbox(context: Context): BoxStore {
//        BoxStore.deleteAllFiles(File(BoxStoreBuilder.DEFAULT_NAME))
        return MyObjectBox.builder()
                .androidContext(context)
                .androidReLinker(ReLinker.log {
                    //                    fun log(message: String) {
                    ////                        Log.d(TAG, message)
                    Logger.println(it)
                    //                    }
                })
                .build();
//        MyObjectBox.builder().androidContext(context).build()
    }
}