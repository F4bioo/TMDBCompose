package com.fappslab.libraries.arch.logger

import timber.log.Timber

class Logger private constructor(private val tag: String) {

    fun v(obj: Any?) {
        Timber.tag(tag).v("Verbose: $obj")
    }

    fun i(obj: Any?) {
        Timber.tag(tag).i("Info: $obj")
    }

    fun e(obj: Any?) {
        Timber.tag(tag).e("Error: $obj")
    }

    companion object {
        fun with(tag: String = "<L>"): Logger =
            Logger(tag)
    }
}
