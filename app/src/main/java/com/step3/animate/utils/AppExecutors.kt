package com.step3.animate.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Author: Meng
 * Date: 2022/08/31
 * Desc:
 */

class AppExecutors private constructor(
    private val mDiskIO: Executor,
    private val mNetworkIO: Executor,
    private val mMainThread: Executor
) {
    private val TAG = "AppExecutors"
    constructor() : this(
        Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    ) {

    }

    fun diskIO(): Executor {
        return mDiskIO
    }

    fun networkIO(): Executor {
        return mNetworkIO
    }

    fun mainThread(): Executor {
        return mMainThread
    }

    private class MainThreadExecutor : Executor {
        private val TAG = "MainThreadExecutor"
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            Log.i(TAG, command.toString())
            mainThreadHandler.post(command)
        }
    }
}
