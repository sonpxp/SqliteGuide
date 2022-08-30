package com.cloudxanh.examplesqlite.database.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cloudxanh.examplesqlite.database.helper.FeedReaderContract.SQL_CREATE_ENTRIES
import com.cloudxanh.examplesqlite.database.helper.FeedReaderContract.SQL_DELETE_ENTRIES


/**
 * Created by sonpxp on 8/30/2022.
 * Email: sonmob202@gmail.com
 * https://developer.android.com/training/data-storage/sqlite
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        // db?.setForeignKeyConstraintsEnabled(true)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"

        // singleton class
        private var mInstance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper? {
            if (mInstance == null) {
                mInstance = DatabaseHelper(context.applicationContext)
            }
            return mInstance
        }
    }
}