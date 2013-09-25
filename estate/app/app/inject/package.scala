package app

import com.google.inject.Guice

/**
 *          Date: 25.09.13
 */
package object inject {
    val injector = Guice.createInjector(new EstateGuiceModule)
}
