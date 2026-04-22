package com.zebpay.catalog

import android.app.Application
import com.zebpay.catalog.base.ShowcaseRegistry
import com.zebpay.catalog.bottomsheet.ZBottomSheetShowcase
import com.zebpay.catalog.buttons.ZButtonShowcase
import com.zebpay.catalog.colors.ColorShowcase
import com.zebpay.catalog.dropdown.ZDropdownShowcase
import com.zebpay.catalog.empty.ZEmptyStateShowcase
import com.zebpay.catalog.header.ZHeaderShowcase
import com.zebpay.catalog.inputfield.ZInputFiledShowcase
import com.zebpay.catalog.keyboard.ZKeyboardShowcase
import com.zebpay.catalog.misc.ZMiscShowcase
import com.zebpay.catalog.pills.ZPillShowcase
import com.zebpay.catalog.tab.ZTabShowcase
import com.zebpay.catalog.tags.ZTagsShowcase
import com.zebpay.catalog.toast.ZToastShowcase
import com.zebpay.catalog.typography.TypographyShowcase

class ZCatalogApp : Application() {

    override fun onCreate() {
        super.onCreate()
        registerModules()
    }
}

fun registerModules() {
    ShowcaseRegistry.register(TypographyShowcase)
    ShowcaseRegistry.register(ColorShowcase)
    ShowcaseRegistry.register(ZButtonShowcase)
    ShowcaseRegistry.register(ZTabShowcase)
    ShowcaseRegistry.register(ZPillShowcase)
    ShowcaseRegistry.register(ZTagsShowcase)
    ShowcaseRegistry.register(ZMiscShowcase)
    ShowcaseRegistry.register(ZHeaderShowcase)
    ShowcaseRegistry.register(ZKeyboardShowcase)
    ShowcaseRegistry.register(ZEmptyStateShowcase)
    ShowcaseRegistry.register(ZDropdownShowcase)
    ShowcaseRegistry.register(ZToastShowcase)
    ShowcaseRegistry.register(ZBottomSheetShowcase)
    ShowcaseRegistry.register(ZInputFiledShowcase)
}