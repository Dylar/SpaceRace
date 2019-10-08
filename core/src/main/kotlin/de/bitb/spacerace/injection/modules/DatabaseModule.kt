package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.items.ItemDataSource
import de.bitb.spacerace.database.items.ItemRespository
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.map.MapRespository
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.database.player.PlayerRespository
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.savegame.SaveDataSource
import de.bitb.spacerace.database.savegame.SaveRespository
import de.bitb.spacerace.model.MyObjectBox
import de.bitb.spacerace.model.items.Item
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder.DEFAULT_NAME
import io.objectbox.kotlin.boxFor
import java.io.File
import javax.inject.Singleton

@Module
class DatabaseModule(
        private var boxStore: BoxStore? = null
) {

    @Provides
    @Singleton
    fun provideDatabase(): BoxStore {
        if (boxStore == null || boxStore?.isClosed == true) {
            BoxStore.deleteAllFiles(File(DEFAULT_NAME))
            boxStore = MyObjectBox.builder().build()
        }
        return boxStore!!
    }

//        val boxStore = MyObjectBox.builder().build()
//        if (BuildConfig.DEBUG) {
//            AndroidObjectBrowser(boxStore).start(application)
//        }
//        return boxStore
//        return BoxStore.getDefault();


    @Provides
    @Singleton
    fun providePlayerBox(store: BoxStore): Box<PlayerData> = store.boxFor(PlayerData::class)

    @Provides
    @Singleton
    fun provideMapDataBox(store: BoxStore): Box<SaveData> = store.boxFor(SaveData::class)

    @Provides
    @Singleton
    fun provideFieldDataBox(store: BoxStore): Box<FieldData> =
            store.boxFor(FieldData::class)

//    @Provides
//    @Singleton
//    fun providePositionBox(store: BoxStore): Box<PositionData> {
//        return store.boxFor(PositionData::class)
//    }

    @Provides
    @Singleton
    fun provideMapDataSource(fieldBox: Box<FieldData>): MapDataSource =
            MapRespository(fieldBox)

    @Provides
    @Singleton
    fun provideSaveDataSource(mapBox: Box<SaveData>): SaveDataSource =
            SaveRespository(mapBox)

    @Provides
    @Singleton
    fun providePlayerDataSource(playerBox: Box<PlayerData>, saveBox: Box<SaveData>): PlayerDataSource =
            PlayerRespository(saveBox, playerBox)

    @Provides
    @Singleton
    fun provideItemBox(store: BoxStore): Box<ItemData> = store.boxFor(ItemData::class.java)

    @Provides
    @Singleton
    fun provideItemDataSource(box: Box<ItemData>): ItemDataSource = ItemRespository(box)

}
