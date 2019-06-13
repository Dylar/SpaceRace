package de.bitb.spacerace.injection.modules

import dagger.Module


@Module
class NetworkModule {
//
//    companion object {
//        private val BASE_URL = "https://api.stackexchange.com"
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideGsonBuilder(): GsonBuilder {
//        val gsonBuilder = GsonBuilder()
////        gsonBuilder.registerTypeAdapter(CheckMail::class.java, CheckMailConverter())
//        return gsonBuilder
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
//        val builder = OkHttpClient.Builder()
//
//        if (BuildConfig.DEBUG) {
//            val loggingInterceptor = HttpLoggingInterceptor()
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
//            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//            builder.addInterceptor(AddHeaderInterceptor())
//                    .addNetworkInterceptor(StethoInterceptor())
//                    .addNetworkInterceptor(loggingInterceptor)
//        }
//
//        return builder
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideRetrofitBuilder(gsonBuilder: GsonBuilder): Retrofit.Builder {
//        return Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
//    }
//
//    @Provides
//    @ApplicationScope
//    fun provideRetrofitConstructions(retrofitBuilder: Retrofit.Builder, httpClientBuilder: OkHttpClient.Builder): RetrofitInterface {
//
//        if (BuildConfig.IS_DEV) {
//            retrofitBuilder.client(httpClientBuilder.build())
//        }
//
//        return retrofitBuilder.build().create(RetrofitInterface::class.java)
//    }

}
