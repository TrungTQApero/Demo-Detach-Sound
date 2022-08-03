package com.apero.videoeditor.extension

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.subscribeOnIo() = this.subscribeOn(Schedulers.io())!!
fun <T> Observable<T>.observeOnMainThread() = this.subscribeOn(AndroidSchedulers.mainThread())!!

inline fun <T> Observable<T>.subscribeUntilDestroy(  observer: KObserver<T>.() -> Unit) {
    return this.observeOn(AndroidSchedulers.mainThread())

            .subscribe(KObserver<T>().apply(observer))
}

class KObserver<T> : Observer<T> {

    private var onNext: ((T) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null
    private var onCompleted: (() -> Unit)? = null

    override fun onNext(t: T) {
        onNext?.invoke(t)
    }

    override fun onError(e: Throwable) {
        onError?.invoke(e)
    }



    fun onNext(function: (T) -> Unit) {
        this.onNext = function
    }

    fun onError(function: (Throwable) -> Unit) {
        this.onError = function
    }

    fun onCompleted(function: () -> Unit) {
        this.onCompleted = function
    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onComplete() {
        onCompleted?.invoke()
    }
}
