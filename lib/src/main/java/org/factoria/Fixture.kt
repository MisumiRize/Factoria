package org.factoria

import android.content.Context

interface Fixture {
    val data: Any
    fun persist(context: Context)
}
