package com.cloudxanh.examplesqlite.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.cloudxanh.examplesqlite.database.helper.DatabaseHelper
import com.cloudxanh.examplesqlite.database.helper.FeedReaderContract
import com.cloudxanh.examplesqlite.model.Feed


/**
 * Created by sonpxp on 8/31/2022.
 * Email: sonmob202@gmail.com
 */
class FeedReaderDao(private val dbHelper: DatabaseHelper /*context: Context*/) {

    // var dbHelper = DatabaseHelper.getInstance(context)

    // Gets the data repository in read, write model
    private val readDb: SQLiteDatabase by lazy { dbHelper.readableDatabase }
    private val writeDb: SQLiteDatabase by lazy { dbHelper.writableDatabase }


    fun loadListFeed(): List<Feed> {
        val projection = arrayOf(
            BaseColumns._ID,
            FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
            FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        )

        // Filter results WHERE "title" = 'My Title'
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} = ?"
        val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE} DESC"

        val cursor = readDb.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )
        val listFeed = mutableListOf<Feed>()

        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val itemTitle =
                    getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE))
                val itemSubTitle =
                    getString(getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE))
                val feed = Feed(itemTitle, itemSubTitle)
                listFeed.add(feed)
            }
        }
        cursor.close()
        return listFeed
    }

    fun loadFeed() {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            BaseColumns._ID,
            FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,
            FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE
        )

        // Filter results WHERE "title" = 'My Title'
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} = ?"
        val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE} DESC"

        val cursor = readDb.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val itemIds = mutableListOf<Long>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                itemIds.add(itemId)
            }
        }
        cursor.close()
    }

    // Create a new map of values, where column names are the keys
    fun insert(feed: Feed) {
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, feed.title)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, feed.subtitle)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = writeDb.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
    }

    fun update(feed: Feed) {
        // New value for one column
        // val title = "MyNewTitle"
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, feed.title)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, feed.subtitle)
        }

        // Which row to update, based on the title
        // val db = dbHelper.writableDatabase
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} LIKE ?"
        // val selectionArgs = arrayOf("MyOldTitle")
        val selectionArgs = arrayOf(feed.title)

        val count = writeDb.update(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }

    fun delete(title: String) {
        // Define 'where' part of query.
        val selection = "${FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE} LIKE ?"
        // Specify arguments in placeholder order.

        // val selectionArgs = arrayOf("MyTitle")
        val selectionArgs = arrayOf(title)
        // Issue SQL statement.
        val deletedRows =
            writeDb.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun deleteAll() {
        val deletedTable = writeDb.delete(FeedReaderContract.FeedEntry.TABLE_NAME, null, null)
    }

}