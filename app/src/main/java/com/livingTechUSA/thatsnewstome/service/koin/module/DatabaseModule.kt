package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.koin.module

import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.NewsDatabase
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.localService.ILocalService
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.localService.LocalServiceProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

val DatabaseModule = module{
    single { NewsDatabase.provideRoomDatabase(androidApplication())}
    single { get<NewsDatabase>().articleDao() }
    single { LocalServiceProvider(get()) } bind ILocalService::class
}