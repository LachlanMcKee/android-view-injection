package net.lachlanmckee.android.view.injection

import android.view.View

import dagger.android.AndroidInjector

interface HasViewInjector {
    val viewInjector: AndroidInjector<View>
}
