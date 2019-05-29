package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.database.PlayerRespository
import de.bitb.spacerace.model.player.MyObjectBox
import de.bitb.spacerace.model.player.PlayerData
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
    }

    @Provides
    @Singleton
    fun providePlayerDataSource(box: Box<PlayerData>): PlayerDataSource {
        return PlayerRespository(box)
    }

}
