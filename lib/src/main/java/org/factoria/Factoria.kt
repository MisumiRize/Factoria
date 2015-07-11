package org.factoria

import android.content.Context
import org.apache.commons.io.FilenameUtils
import org.factoria.pool.ClassPool

class Factoria(testContext: Context,
               val appContext: Context,
               poolType: Configuration.PoolType = Configuration.PoolType.CLASS) {

    val fixture = poolType.getPool(appContext)

    init {
        val assetManager = testContext.getAssets()
        assetManager.list("").forEach { asset ->
            if (asset.endsWith(".yml")) {
                fixture.addFile(FilenameUtils.getBaseName(asset), assetManager.open(asset))
            }
        }
    }

    fun create(key: String): Any {
        val f = fixture.pool.get(key)!!
        f.persist(appContext)
        return f.data
    }

    fun build(key: String): Any {
        return fixture.pool.get(key)!!.data
    }

}
