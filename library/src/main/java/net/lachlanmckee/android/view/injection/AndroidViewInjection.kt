package net.lachlanmckee.android.view.injection

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View

object AndroidViewInjection {
    fun inject(view: View) {
        getHasViewInjector(view.context)
            .viewInjector
            .inject(view)
    }

    private fun getHasViewInjector(context: Context): HasViewInjector {
        return getFromActivity(context)
            ?: getFromApplication(context)
    }

    private fun getFromActivity(context: Context): HasViewInjector? {
        var currentContext = context
        while (currentContext is ContextWrapper) {
            if (currentContext is Activity && currentContext is HasViewInjector) {
                return currentContext
            }
            currentContext = currentContext.baseContext
        }
        return null
    }

    private fun getFromApplication(context: Context): HasViewInjector {
        val application = context.applicationContext
        if (application is HasViewInjector) {
            return application
        }
        throw RuntimeException(
            String.format(
                "%s does not implement %s",
                application.javaClass.canonicalName,
                HasViewInjector::class.java.canonicalName
            )
        )
    }
}