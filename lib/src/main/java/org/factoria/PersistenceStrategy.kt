package org.factoria

interface PersistenceStrategy {
    fun persist(data: Any, klass: Class<*>)
}
