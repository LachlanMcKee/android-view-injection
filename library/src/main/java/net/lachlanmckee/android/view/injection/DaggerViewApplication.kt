package net.lachlanmckee.android.view.injection

import android.view.View
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

abstract class DaggerViewApplication : dagger.android.DaggerApplication(), HasViewInjector {
    abstract val daggerComponent: Any

    override val viewInjector: AndroidInjector<View> by lazy {
        DispatchingAndroidInjector_Factory.newInstance<View>(
            getMapByClass(),
            getMapByString()
        )
    }

    private fun getMapByClass(): Map<Class<*>, Provider<AndroidInjector.Factory<*>>> {
        return callDaggerMethod("getMapOfClassOfAndProviderOfAndroidInjectorFactoryOf")
            ?: emptyMap()
    }

    private fun getMapByString(): Map<String, Provider<AndroidInjector.Factory<*>>> {
        return callDaggerMethod("getMapOfStringOfAndProviderOfAndroidInjectorFactoryOf")
            ?: emptyMap()
    }

    private inline fun <reified T> callDaggerMethod(methodName: String): T? {
        return daggerComponent.let { component ->
            requireNotNull(daggerComponent) { "Dagger component must not be null" }
            try {
                val method = component.javaClass
                    .getDeclaredMethod(methodName)
                method.isAccessible = true
                method.invoke(component) as T
            } catch (e: Exception) {
                null
            }
        }
    }
}
