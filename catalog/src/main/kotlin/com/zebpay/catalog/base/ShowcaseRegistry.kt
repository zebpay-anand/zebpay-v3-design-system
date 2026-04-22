package com.zebpay.catalog.base

object ShowcaseRegistry {
    private val modules = mutableListOf<ShowcaseModule>()

    fun register(module: ShowcaseModule) {
        modules.add(module)
    }

    fun unregister(module: ShowcaseModule) {
        modules.remove(module)
    }

    fun getModules(): List<ShowcaseModule> = modules
}