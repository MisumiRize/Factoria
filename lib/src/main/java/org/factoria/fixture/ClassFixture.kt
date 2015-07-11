package org.factoria.fixture

import android.content.Context
import org.factoria.AutoLoader
import org.factoria.Fixture

class ClassFixture(override val data: Any, val klass: Class<*>) : Fixture {

    override fun persist(context: Context) {
        AutoLoader.loadOrm(klass).getPersistenceStrategy(context).persist(data, klass)
    }

}
