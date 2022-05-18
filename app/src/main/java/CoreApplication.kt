package com.livingTechUSA.thatsnewstome

import android.app.Application
import android.content.Context
import android.util.Log
import com.axxess.palliative.service.koin.module.NetworkModule
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.service.koin.module.DatabaseModule
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import java.io.File
import java.lang.Exception

class CoreApplication : Application(), KoinComponent {

    companion object {
        private lateinit var sInstance: CoreApplication

        @JvmStatic
        fun getInstance(): CoreApplication = sInstance

    }


    override fun onCreate() {
        super.onCreate()
        sInstance = this

        initKoin()

        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        )
    }


    fun initKoin() {
        startKoin {
            logger(PrintLogger(Level.ERROR)) //Required to use navigation components and prevent crash at run time. See https://github.com/InsertKoinIO/koin/issues/1188
            androidContext(this@CoreApplication)
            modules(
                listOf(
                    NetworkModule,
                    DatabaseModule
                )
            )
        }
    }

}