package com.raywenderlich.android.schedulers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.BehaviorSubject

class SchedulersActivity : AppCompatActivity() {

  private val disposables = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_schedulers)

    val animal = BehaviorSubject.createDefault("[dog]")

    animal
      .dump()
      .dumpingSubscription()

    val fruit = Observable.create<String> { observer ->
      observer.onNext("[apple]")
      Thread.sleep(2000)
      observer.onNext("[pineapple]")
      Thread.sleep(2000)
      observer.onNext("[strawberry]")
    }

    fruit
      .dump()
      .dumpingSubscription()
      .addTo(disposables)
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.dispose()
  }
}
