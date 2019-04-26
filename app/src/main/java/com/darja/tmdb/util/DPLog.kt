@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.darja.tmdb.util

object DPLog {
    private const val TAG = "Tmdb"

    private var ENABLED = true
    private const val LOCATION_ENABLED = true

    private val location: String
        get() {
            @Suppress("ConstantConditionIf")
            if (!LOCATION_ENABLED)
                return ""

            val logClassName = DPLog::class.java.name
            val traces = Thread.currentThread().stackTrace
            var found = false

            for (i in traces.indices) {
                val trace = traces[i]

                if (found) {
                    if (!trace.className.startsWith(logClassName)) {
                        return String.format(" (%s:%d)", trace.fileName, trace.lineNumber)
                    }
                } else if (trace.className.startsWith(logClassName)) {
                    found = true
                }
            }

            return " []"
        }

    fun tag(tag: String) = "$TAG.$tag"

    fun disable() {
        ENABLED = false
    }

    fun vt(tag: String, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.v(tag, String.format(format, *args) + location)
    }

    fun dt(tag: String, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.d(tag, String.format(format, *args) + location)
    }

    fun it(tag: String, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.i(tag, String.format(format, *args) + location)
    }

    fun wt(tag: String, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.w(tag, String.format(format, *args) + location)
    }

    fun wt(tag: String, message: String, e: Throwable) {
        if (ENABLED) android.util.Log.w(tag, message + location, e)
    }

    fun wt(tag: String, e: Throwable) {
        if (ENABLED) android.util.Log.w(tag, e)
    }

    fun et(tag: String, e: Throwable?) {
        if (ENABLED) android.util.Log.e(tag, (e?.message ?: "NO EXCEPTION") + location, e)
    }

    fun et(tag: String, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.e(tag, String.format(format, *args) + location)
    }

    fun v(format: String, vararg args: Any?) {
        vt(TAG, format, *args)
    }

    fun d(format: String, vararg args: Any?) {
        dt(TAG, format, *args)
    }

    fun i(format: String, vararg args: Any?) {
        it(TAG, format, *args)
    }

    fun w(format: String, vararg args: Any?) {
        wt(TAG, format, *args)
    }

    fun w(message: String, e: Throwable) {
        wt(TAG, message, e)
    }

    fun w(e: Throwable) {
        wt(TAG, e)
    }

    fun e(format: String, vararg args: Any?) {
        et(TAG, format, *args)
    }

    fun e(e: Throwable?) {
        et(TAG, e)
    }

    fun vtrace(traceLength: Int, format: String, vararg args: Any?) {
        vtrace(TAG, traceLength, format, *args)
    }

    fun dtrace(traceLength: Int, format: String, vararg args: Any?) {
        dtrace(TAG, traceLength, format, *args)
    }

    fun itrace(traceLength: Int, format: String, vararg args: Any?) {
        itrace(TAG, traceLength, format, *args)
    }

    fun wtrace(traceLength: Int, format: String, vararg args: Any?) {
        wtrace(TAG, traceLength, format, *args)
    }

    fun etrace(traceLength: Int, format: String, vararg args: Any?) {
        etrace(TAG, traceLength, format, *args)
    }

    fun vtrace(tag: String, traceLength: Int, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.v(tag, String.format(format, *args) + getTrace(traceLength))
    }

    fun dtrace(tag: String, traceLength: Int, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.d(tag, String.format(format, *args) + getTrace(traceLength))
    }

    fun itrace(tag: String, traceLength: Int, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.i(tag, String.format(format, *args) + getTrace(traceLength))
    }

    fun wtrace(tag: String, traceLength: Int, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.w(tag, String.format(format, *args) + getTrace(traceLength))
    }

    fun etrace(tag: String, traceLength: Int, format: String, vararg args: Any?) {
        if (ENABLED) android.util.Log.e(tag, String.format(format, *args) + getTrace(traceLength))
    }

    @JvmOverloads
    fun checkpoint(tag: String = TAG) {
        val logClassName = DPLog::class.java.name
        val traces = Thread.currentThread().stackTrace
        var foundIndex = -1

        for (i in traces.indices) {
            val trace = traces[i]

            if (trace.className.startsWith(logClassName)) {
                foundIndex = i
            } else {
                if (foundIndex > 0)
                    break
            }
        }

        val trace = traces[foundIndex + 1]
        val message = String.format("%s.%s (%s:%s)", trace.className, trace.methodName,
            trace.fileName, trace.lineNumber)

        android.util.Log.v(tag, message)
    }

    private fun getTrace(length: Int): String {
        @Suppress("ConstantConditionIf")
        if (!LOCATION_ENABLED)
            return ""

        val logClassName = DPLog::class.java.name
        val traces = Thread.currentThread().stackTrace
        var foundIndex = -1

        for (i in traces.indices) {
            val trace = traces[i]

            if (trace.className.startsWith(logClassName)) {
                foundIndex = i
            } else {
                if (foundIndex > 0)
                    break
            }
        }

        val sb = StringBuilder()
        sb.append("\n")
        for (i in foundIndex + 1 until foundIndex + length + 1) {
            if (i > traces.size)
                break

            val trace = traces[i]
            sb.append(String.format("    at %s.%s (%s:%s)\n",
                trace.className, trace.methodName,
                trace.fileName, trace.lineNumber))
        }
        sb.delete(sb.length - 1, sb.length)
        return "\n" + sb.toString()
    }
}