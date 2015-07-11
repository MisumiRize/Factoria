package org.factoria

import java.io.InputStream

interface FixturePool {
    val pool: Map<String, Fixture>
    fun addFile(basename: String, iStream: InputStream)
}
