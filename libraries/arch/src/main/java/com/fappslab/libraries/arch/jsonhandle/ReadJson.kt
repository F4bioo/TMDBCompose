package com.fappslab.libraries.arch.jsonhandle

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileNotFoundException

inline fun <reified DataModel> readFromModelToJSON(model: DataModel?): String =
    Gson().toJson(model)

fun readFromJSONToString(jsonFileName: String): String =
    jsonFileName.toBufferedReader().use { reader ->
        reader?.readText().toString()
    }

fun readFromJSONToStrings(jsonFile: String): String =
    requireNotNull(ClassLoader.getSystemResource(jsonFile)?.readText()) {
        throw FileNotFoundException("File $jsonFile not found!")
    }

inline fun <reified DataModel> readFromJSONToModel(jsonFileName: String): DataModel =
    jsonFileName.toBufferedReader().use { reader ->
        Gson().fromJson(reader, DataModel::class.java)
    }

fun String.toBufferedReader(): BufferedReader? {
    return Thread.currentThread()
        .contextClassLoader?.getResourceAsStream(this)?.bufferedReader()
}
