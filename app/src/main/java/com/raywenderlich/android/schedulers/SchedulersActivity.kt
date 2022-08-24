package com.raywenderlich.android.schedulers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
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
      .subscribeOn(Schedulers.io())
      .dump()
      .observeOn(AndroidSchedulers.mainThread())
      .dumpingSubscription()
      .addTo(disposables)

    val animalsThread = Thread {
      Thread.sleep(3000)
      animal.onNext("[cat]")
      Thread.sleep(3000)
      animal.onNext("[tiger]")
      Thread.sleep(3000)
      animal.onNext("[fox]")
      Thread.sleep(3000)
      animal.onNext("[leopard]")
    }

    animalsThread.name = "Animals Thread"
    animalsThread.start()
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.dispose()
  }
}
