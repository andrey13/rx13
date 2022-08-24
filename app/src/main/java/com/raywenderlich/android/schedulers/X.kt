package com.raywenderlich.android.schedulers

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*

private const val TAG = "SchedulerLogging"

fun <T> Observable<T>.dump(): Observable<T> {
  var date: Date? = null
  return doOnSubscribe { date = Date() }
    .doOnNext {
      Log.d(TAG, "${secondsElapsed(date!!)}s | [D] $it received on Thread: ${Thread.currentThread().name}")
    }
}

private fun secondsElapsed(fromDate: Date): Long {
  return (Date().time - fromDate.time) / 1000
}

fun Observable<*>.dumpingSubscription(): Disposable {
  var date: Date? = null
  return doOnSubscribe { date = Date() }
    .subscribe {
      Log.d(TAG, "${secondsElapsed(date!!)}s | [S] $it received on Thread: ${Thread.currentThread().name}")
    }
}

