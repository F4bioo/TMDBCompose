package com.fappslab.libraries.arch.testing.robot

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import kotlin.reflect.KClass

interface RobotScreen {
    val composeTestRule: ComposeContentTestRule
}

fun <T : Any> KClass<T>.createInstance(composeTestRule: ComposeContentTestRule): T {
    val constructor = this.constructors.find { constructor ->
        constructor.parameters.size == 1 &&
                constructor.parameters.first().type.classifier == ComposeContentTestRule::class
    }

    return requireNotNull(constructor) {
        throw IllegalArgumentException("No suitable constructor found for class ${this.simpleName}")
    }.call(composeTestRule)
}
