package com.kaisebhi.githubproject.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AllRepoEntity::class], exportSchema = false, version = 1)
@TypeConverters(com.kaisebhi.githubproject.data.network.TypeConverters::class)
abstract class DatabaseRoom: RoomDatabase(){
    abstract fun getAllRepoDao(): AllRepoDao

    companion object {
        @Volatile
        private var instance: DatabaseRoom? = null
        private val DB_NAME = "localDb"

        /**Below is migration code of database*/
//        val migration1_2 = object: Migration(1, 2) {
//            override fun migrate(supportSQLiteDb: SupportSQLiteDatabase) {
//                //Execute your SQLite statements to upgrade SQLite database.
//                supportSQLiteDb.execSQL("CREATE TABLE teachersDetails")
//            }
//        }

        /**Below method returns RoomDb instance */
        fun getDatabase(applicationContext: Context): DatabaseRoom {
            if(instance == null) {
                synchronized(this) {
                    return Room.
                    databaseBuilder(applicationContext, DatabaseRoom::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return instance!!
        }
    }
}