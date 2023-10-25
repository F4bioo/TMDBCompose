package com.fappslab.tmdbcompose.core.data.common.extension

fun Any?.isNull(): Boolean = this == null

fun Any?.isNotNull(): Boolean = !this.isNull()
