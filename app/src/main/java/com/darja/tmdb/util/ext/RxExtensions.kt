package com.darja.tmdb.util.ext

import com.darja.tmdb.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> =
        observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun Completable.with(schedulerProvider: SchedulerProvider): Completable =
        observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun <T> Observable<T>.with(schedulerProvider: SchedulerProvider): Observable<T> =
        observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun <T> Flowable<T>.with(schedulerProvider: SchedulerProvider): Flowable<T> =
        observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun Disposable.addTo(subscription: CompositeDisposable) = subscription.add(this)
