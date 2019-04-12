package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.model.player.PlayerData
//import io.objectbox.Box
//import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class DatabaseModule() {

//    @Provides
//    @Singleton
//    fun provideDatabase(): BoxStore {
//        val boxStore = MyObjectBox.builder().build()
////        if (BuildConfig.DEBUG) {
////            AndroidObjectBrowser(boxStore).start(application)
////        }
//        return boxStore
//    }
//
//    @Provides
//    @Singleton
//    fun providePlayerBox(store: BoxStore): Box<PlayerData> {
//        return store.boxFor(PlayerData::class.java)
//    }

//    @Provides
//    @Singleton
//    fun provideDeviceDBHandler(application: DBSystelApplication): DeviceDBHandler {
//        return DeviceDBHandler(application)
//    }

}
