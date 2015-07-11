package org.factoria

import android.content.Context
import org.factoria.pool.ClassPool
import org.factoria.pool.ValuePool

class Configuration(val appContext: Context,
                    val testContext: Context,
                    val poolType: Configuration.PoolType = Configuration.PoolType.CLASS) {

    enum class PoolType {
        CLASS {
            override fun getPool(context: Context) = ClassPool(context)
        },
        VALUE {
            override fun getPool(context: Context) = ValuePool()
        };

        abstract fun getPool(context: Context): FixturePool
    }

}
