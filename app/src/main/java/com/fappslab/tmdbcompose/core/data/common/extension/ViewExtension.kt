package com.fappslab.tmdbcompose.core.data.common.extension

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import kotlin.reflect.KClass

fun <P : Parcelable> Bundle.toParcelable(key: String, clazz: KClass<P>): P? {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        @Suppress("DEPRECATION") getParcelable(key)
    } else getParcelable(key, clazz.java)
}
