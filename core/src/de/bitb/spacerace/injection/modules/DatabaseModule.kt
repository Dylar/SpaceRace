package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule() {

    @Provides
    @Singleton
    fun provideDatabase(application: DBSystelApplication): BoxStore {
        val boxStore = MyObjectBox.builder().androidContext(application).build()
        if (BuildConfig.DEBUG) {
            AndroidObjectBrowser(boxStore).start(application)
        }
        return boxStore
    }

    @Provides
    @Singleton
    fun provideDeviceBox(store: BoxStore): Box<Device> {
        return store.boxFor(Device::class.java)
    }

    @Provides
    @Singleton
    fun provideDeviceDBHandler(application: DBSystelApplication): DeviceDBHandler {
        return DeviceDBHandler(application)
    }

}
