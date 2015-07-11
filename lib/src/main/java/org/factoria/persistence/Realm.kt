package org.factoria.persistence

import android.content.Context
import org.factoria.PersistenceStrategy

class Realm(val context: Context) : PersistenceStrategy {

    val realm: Any

    init {
        realm = Class.forName("io.realm.Realm")
                .getMethod("getInstance", javaClass<Context>())
                .invoke(null, context)
    }

    override fun persist(data: Any, klass: Class<*>) {
        val realmClass = realm.javaClass
        realmClass.getMethod("beginTransaction").invoke(realm)
        realmClass.getMethods().first { method ->
            method.getName() == "copyToRealmOrUpdate"
        }.invoke(realm, data)
        realmClass.getMethod("commitTransaction").invoke(realm)
        realmClass.getMethod("close").invoke(realm)
    }

}
