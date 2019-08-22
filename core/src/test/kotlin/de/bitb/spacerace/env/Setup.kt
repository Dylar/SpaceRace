package de.bitb.spacerace.env

import de.bitb.spacerace.model.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.DebugFlags
import java.io.File


class Setup(
) {

    companion object {
        val TEST_DIRECTORY = File("object-store-test")
        var boxStore: BoxStore? = null

        fun createMockBoxStore(): BoxStore {

            boxStore?.close()
            boxStore?.deleteAllFiles()

            BoxStore.deleteAllFiles(TEST_DIRECTORY)
            boxStore = MyObjectBox.builder()
                    // add directory flag to change where ObjectBox puts its database files
                    .directory(TEST_DIRECTORY)
                    // optional: add debug flags for more detailed ObjectBox log output
                    .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
                    .build()

            return boxStore!!
        }
    }

    init {
        mockWifi()
    }

    fun mockWifi(wifiEnabled: Boolean = true): Setup {
//        every { networkUtils.isNetworkAvailable } returns wifiEnabled
        return this
    }

}