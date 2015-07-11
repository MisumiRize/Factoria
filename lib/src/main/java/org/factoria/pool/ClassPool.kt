package org.factoria.pool

import android.content.Context
import dalvik.system.DexFile
import org.factoria.fixture.ClassFixture
import org.factoria.FixturePool
import org.yaml.snakeyaml.Yaml
import java.io.InputStream
import java.util.*
import kotlin.text.Regex

class ClassPool(val context: Context) : FixturePool {

    val yamlLoader = Yaml()
    override val pool = HashMap<String, ClassFixture>()

    override fun addFile(basename: String, iStream: InputStream) {
        val className = basename.capitalize()
        val fullName = DexFile(context.getPackageCodePath()).entries().asSequence().first { klass ->
            klass.splitBy(".").last() == className
        }
        val klass = Class.forName(fullName)

        @suppress("UNCHECKED_CAST")
        val yamlData = yamlLoader.load(iStream) as Map<String, Map<String, Any>>

        yamlData.forEach { entry ->
            val constructor = klass.getDeclaredConstructor()
            constructor.setAccessible(true)
            val obj = constructor.newInstance()

            entry.value.forEach { field ->
                val f = klass.getDeclaredField(field.key)
                f.setAccessible(true)
                f.set(obj, field.value)
            }

            pool.put(entry.key, ClassFixture(obj!!, klass))
        }
    }

}
