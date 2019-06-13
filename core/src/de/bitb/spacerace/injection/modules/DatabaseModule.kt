package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.database.PlayerRespository
import de.bitb.spacerace.model.MyObjectBox
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.FieldData
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder.DEFAULT_NAME
import java.io.File
import javax.inject.Singleton

@Module
class DatabaseModule() {

    @Provides
    @Singleton
    fun provideDatabase(): BoxStore {
        BoxStore.deleteAllFiles(File(DEFAULT_NAME))
//        val boxStore = MyObjectBox.builder().build()
//        if (BuildConfig.DEBUG) {
//            AndroidObjectBrowser(boxStore).start(application)
//        }
//        return boxStore
//        return BoxStore.getDefault();
        return MyObjectBox.builder().build()
    }

    @Provides
    @Singleton
    fun providePlayerBox(store: BoxStore): Box<PlayerData> {
        return store.boxFor(PlayerData::class.java)
//        return store.boxFor(PlayerData::class)
    }

//    @Provides
//    @Singleton
//    fun provideMapDataBox(store: BoxStore): Box<MapData> {
//        return store.boxFor(MapData::class)
//    }
//
    @Provides
    @Singleton
    fun provideFieldDataBox(store: BoxStore): Box<FieldData> {
    return store.boxFor(FieldData::class.java)
//        return store.boxFor(FieldData::class)
    }

//    @Provides
//    @Singleton
//    fun providePositionBox(store: BoxStore): Box<PositionData> {
//        return store.boxFor(PositionData::class)
//    }

//    @Provides
//    @Singleton
//    fun provideMapDataSource(): MapDataSource {
//        return MapRespository()
//    }

    @Provides
    @Singleton
    fun providePlayerDataSource(box: Box<PlayerData>): PlayerDataSource {
        return PlayerRespository(box)
    }

}
