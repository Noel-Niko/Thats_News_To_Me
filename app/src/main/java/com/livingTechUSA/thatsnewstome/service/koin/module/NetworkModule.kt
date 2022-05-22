package com.axxess.palliative.service.koin.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.api.NewsWebServiceProvider
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.api.NewsWebServices
import com.livingTechUSA.thatsnewstome.service.coroutines.AppDispatchers
import com.livingTechUSA.thatsnewstome.service.coroutines.IAppDispatchers
import org.koin.dsl.bind
import org.koin.dsl.module


val NetworkModule = module {

    single { provideStethoInterceptor() }
    single { provideAppDispatcher() }
    single { NewsWebServiceProvider() } bind NewsWebServices::class

}

fun provideAppDispatcher(): IAppDispatchers = AppDispatchers()

fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()


