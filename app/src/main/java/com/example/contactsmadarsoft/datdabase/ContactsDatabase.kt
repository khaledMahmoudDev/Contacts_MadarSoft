package com.example.contactsmadarsoft.datdabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactsmadarsoft.models.ContactsModel

@Database(entities = [ContactsModel::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract val contactsDatabaseDAO: ContactsDatabaseDAO

    companion object {

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase {
            synchronized(lock = this)
            {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, ContactsDatabase::class.java,
                        "contacts_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}