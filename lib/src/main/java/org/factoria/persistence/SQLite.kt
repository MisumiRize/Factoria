package org.factoria.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dalvik.system.DexFile

object SQLite {

    var db: SQLiteDatabase? = null

    fun getInstance(context: Context): SQLiteDatabase {
        if (db != null && db!!.isOpen()) {
            return db!!
        }

        val helperClassName = DexFile(context.getPackageCodePath()).entries().asSequence().first { className ->
            try {
                val klass = Class.forName(className)
                javaClass<SQLiteOpenHelper>().isAssignableFrom(klass)
            }
            catch (ex: NoClassDefFoundError) {
                false
            }
            catch (ex: ClassNotFoundException) {
                false
            }
        }
        val helperClass = Class.forName(helperClassName)
        val helper = helperClass.getDeclaredConstructor(javaClass<Context>())
                .newInstance(context)
        db = helperClass.getMethod("getWritableDatabase")
                .invoke(helper) as SQLiteDatabase

        return db!!
    }

}
