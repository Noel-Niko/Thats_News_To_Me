package com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.dao.ArticleDao
import com.livingTechUSA.thatsnewstome.com.livingTechUSA.thatsnewstome.database.entity.ArticleEntity
import com.livingTechUSA.thatsnewstome.util.Constant


@Database(
    entities = [ ArticleEntity::class] ,
    version = 1,
    exportSchema = false
)



abstract class NewsDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract fun articleDao(): ArticleDao

    companion object {
        fun provideRoomDatabase(context: Context): NewsDatabase {
            var databaseInstance: NewsDatabase? = null

            return databaseInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    Constant.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                databaseInstance = instance
                instance
            }
        }
    }
}
