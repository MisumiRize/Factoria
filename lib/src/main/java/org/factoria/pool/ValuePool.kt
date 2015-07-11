package org.factoria.pool

import android.content.ContentValues
import android.content.Context
import org.factoria.FixturePool
import org.factoria.fixture.TableFixture
import org.yaml.snakeyaml.Yaml
import java.io.InputStream
import java.util.*

class ValuePool() : FixturePool {

    val yamlLoader = Yaml()
    override val pool = HashMap<String, TableFixture>()

    override fun addFile(basename: String, iStream: InputStream) {
        @suppress("UNCHECKED_CAST")
        val yamlData = yamlLoader.load(iStream) as Map<String, Map<String, Any>>

        yamlData.forEach { entry ->
            val value = ContentValues()

            entry.value.forEach { field ->
                when (field.value.javaClass) {
                    javaClass<java.lang.Boolean>() -> value.put(field.key, field.value as Boolean)
                    javaClass<java.lang.Byte>() -> value.put(field.key, field.value as Byte)
                    javaClass<ByteArray>() -> value.put(field.key, field.value as ByteArray)
                    javaClass<java.lang.Double>() -> value.put(field.key, field.value as Double)
                    javaClass<java.lang.Float>() -> value.put(field.key, field.value as Float)
                    javaClass<java.lang.Integer>() -> value.put(field.key, field.value as Int)
                    javaClass<java.lang.Long>() -> value.put(field.key, field.value as Long)
                    javaClass<java.lang.Short>() -> value.put(field.key, field.value as Short)
                    javaClass<java.lang.String>() -> value.put(field.key, field.value as String)
                    else -> throw RuntimeException("invalid field value given: " + field.value.toString())
                }
            }

            pool.put(entry.key, TableFixture(basename, value))
        }
    }

}
