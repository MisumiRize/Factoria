package org.factoria.realmapp

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest
import io.realm.Realm
import org.factoria.Factoria
import org.factoria.realmapp.nestedpackage.Bar
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.soylentsystem.SoylentSystem
import kotlin.test.assertEquals

LargeTest RunWith(AndroidJUnit4::class) class FactoriaTest {

    var context: Context? = null
    var factoria: Factoria? = null
    var realm: Realm? = null

    Before fun setUp() {
        context = InstrumentationRegistry.getTargetContext()
        factoria = Factoria(InstrumentationRegistry.getContext(), context!!)
        realm = Realm.getInstance(context!!)
    }

    Test fun createWillPersistDataToRealm() {
        @suppress("UNCHECKED_CAST")
        val foo = factoria!!.create("foo") as? Foo
        assertEquals(foo!!.getName(), "test")

        val dataSet = realm!!.allObjects(javaClass<Foo>())
        assertEquals(dataSet.size(), 1)
    }

    Test fun buildWillNotPersistDataToRealm() {
        @suppress("UNCHECKED_CAST")
        val foo = factoria!!.build("foo") as? Foo
        assertEquals(foo!!.getName(), "test")

        val dataSet = realm!!.allObjects(javaClass<Foo>())
        assertEquals(dataSet.size(), 0)
    }

    Test fun createWillPersistDataToRealmOnNestedPackage() {
        @suppress("UNCHECKED_CAST")
        val bar = factoria!!.create("bar") as? Bar
        assertEquals(bar!!.getName(), "test")

        val dataSet = realm!!.allObjects(javaClass<Bar>())
        assertEquals(dataSet.size(), 1)
    }

    After fun tearDownRealm() {
        realm?.close()
        SoylentSystem.clean(context!!)
    }

}
