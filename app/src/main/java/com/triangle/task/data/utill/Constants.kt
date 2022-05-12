package com.triangle.task.data.utill

import android.content.Context
import android.content.pm.ApplicationInfo


object UrlConstants{
    const val BASE_APPSCRIPT_URL = "https://reqres.in/"
}

object DebugMode{
    fun isDebug(context: Context) = (0 != context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)
}