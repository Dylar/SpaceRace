#OBJECTBOX BUG
-keep class com.getkeepsafe.relinker.** { *; }
-keep class com/getkeepsafe/relinker/ReLinker.class
-keep class com/getkeepsafe/relinker/ReLinkerInstance.class
-keep class com/getkeepsafe/relinker/SystemLibraryLoader.class
-keep class io/objectbox/BoxStoreBuilder.class
-keep class io/objectbox/BoxStoreFactory.class
-keep class io/objectbox/BoxStore.class
-keep class io/objectbox/Box.class
-keep class io/objectbox/Cursor.class
-keep class io/objectbox/internal/NativeLibraryLoader.class
-keep class io.objectbox.** { *; }