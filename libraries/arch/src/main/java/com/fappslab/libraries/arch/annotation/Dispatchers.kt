package com.fappslab.libraries.arch.annotation

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IO

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Main
