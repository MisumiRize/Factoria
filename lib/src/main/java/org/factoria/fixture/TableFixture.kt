package org.factoria.fixture

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dalvik.system.DexFile
import org.factoria.Fixture
import org.factoria.persistence.SQLite

class TableFixture(val tableName: String, override val data: ContentValues) : Fixture {

    override fun persist(context: Context) {
        SQLite.getInstance(context).insert(tableName, null, data)
    }

}
