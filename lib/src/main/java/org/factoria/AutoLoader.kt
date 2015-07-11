package org.factoria

import android.content.Context
import org.factoria.persistence.Realm

object AutoLoader {

    fun loadOrm(klass: Class<*>): Orm {
        Orm.values().forEach { orm ->
            try {
                val ormKlass = Class.forName(orm.klass)
                if (ormKlass.isAssignableFrom(klass)) {
                    return orm
                }
            }
            catch (ex: ClassNotFoundException) { }
        }
        throw RuntimeException("orm not found")
    }

    enum class Orm(val klass: String) {
        REALM("io.realm.RealmObject") {
            override fun getPersistenceStrategy(context: Context) = Realm(context)
        };

        abstract fun getPersistenceStrategy(context: Context): PersistenceStrategy
    }

}
