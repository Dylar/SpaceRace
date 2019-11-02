package de.bitb.spacerace.env

import de.bitb.spacerace.model.MyObjectBox
import io.objectbox.BoxStore
import java.io.File


class TestSystemSetup(
) {

    companion object {
        var boxStore: BoxStore? = null

        fun createMockBoxStore(): BoxStore {

            boxStore?.close()
            boxStore?.deleteAllFiles()
            val file = File("objectboxTest")
            BoxStore.deleteAllFiles(file)
            boxStore = MyObjectBox.builder()
                    // add directory flag to change where ObjectBox puts its database files
                    .directory(file)
                    // optional: add debug flags for more detailed ObjectBox log output
//                    .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
                    .build()

            return boxStore!!
        }
    }

    init {
        mockWifi()
    }

    fun mockWifi(wifiEnabled: Boolean = true): TestSystemSetup {
//        every { networkUtils.isNetworkAvailable } returns wifiEnabled
        return this
    }

}