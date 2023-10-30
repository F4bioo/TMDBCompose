package com.fappslab.features.detail.presentation.robot

interface RobotCheck<RC : RobotCheck<RC>> {
    @Suppress("UNCHECKED_CAST")
    fun thenCheck(block: RC.() -> Unit): RC {
        block(this as RC)
        return this
    }
}
