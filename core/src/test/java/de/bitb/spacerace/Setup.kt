package de.bitb.spacerace

import de.bitb.spacerace.model.MyObjectBox
import io.mockk.every
import io.objectbox.BoxStore
import java.io.File

class Setup(
        mockContext: MockData
) : MockData by mockContext {

    companion object {
        fun createMockBoxStore(): BoxStore {
            val tempFile = File.createTempFile("object-store-test", "")
            tempFile.delete()
            return MyObjectBox.builder()
                    .directory(tempFile)
                    .build()
        }
    }

    init {
        mockWifi()
    }

    fun mockWifi(wifiEnabled: Boolean = true): Setup {
//        every { networkUtils.isNetworkAvailable } returns wifiEnabled
        return this
    }

//    fun mockExpAnfrageSet(jsonData: String = MOCK_EXP_ANFRAGE_SET): Setup {
//        mockAssets.subscribeBy {
//            val moshi = Moshi.Builder().build()
//            val orders = moshi.adapter<ExpAnfrageSet>(ExpAnfrageSet::class.java)
//            val expAnfrageSet = orders.fromJson(it.open(jsonData).bufferedReader().use { it.readText() })
//            every { thmRestApi.getExpAnfrageSet(any(), any(), any()) } returns Single.just(expAnfrageSet)
//        }.apply { }
//        return this
//    }
//
//    fun mockExpThmTabSet(jsonData: String = MOCK_EXP_THM_TAB): Setup {
//        mockAssets.subscribeBy {
//            val expAnfrageSet = expThmTabSet(it, jsonData)
//            every { thmRestApi.getExpTHMTabSet(any()) } returns Single.just(expAnfrageSet)
//        }.apply { }
//        return this
//    }
//
//    private fun loadPropertiesFile(propertyFile: String) {
//        val appProps =
//                File(Environment.getExternalStorageDirectory().path + Constants.FILEPATH_ROOT, Constants.PROPERTIES_FILE)
//
//        mockAssets.subscribeBy {
//            val openFd = it.openFd(propertyFile)
//            val outputStream = FileOutputStream(appProps)
//            openFd.createInputStream()
//                    .channel
//                    .transferTo(openFd.startOffset, openFd.length, outputStream.channel)
//
//            openFd.close()
//            outputStream.close()
//        }.apply { }
//    }
}