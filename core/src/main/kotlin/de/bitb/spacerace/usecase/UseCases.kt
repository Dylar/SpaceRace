package de.bitb.spacerace.usecase

import de.bitb.spacerace.core.utils.Logger
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

val defaultWorkerThread = GdxSchedulers.workerThread
val defaultSubscriberThread = GdxSchedulers.mainThread
val defaultOnError: (Throwable) -> Unit = {
    Logger.printLog("Default onError: ${it::class.simpleName}")
    it.printStackTrace()
}

interface DisposableContainer {
    val compositeDisposable: CompositeDisposable

    fun Disposable.addDisposable() {
        compositeDisposable += this
    }

    fun disposeDisposables() {
        compositeDisposable.clear()
    }
}

interface ExecuteUseCase<in Params> {

    fun buildUseCaseCompletable(params: Params): Completable

    fun execute(
            params: Params,
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onComplete: () -> Unit = {}
    ): Disposable = Completable.complete()
            .observeOn(workerScheduler)
            .andThen(buildUseCaseCompletable(params))
            .observeOn(observerScheduler)
            .subscribeBy(
                    onComplete = onComplete,
                    onError = onError
            )
}

interface ExecuteUseCaseNoParams {

    fun buildUseCaseCompletable(): Completable

    fun execute(
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onComplete: () -> Unit = {}
    ): Disposable = Completable.complete()
            .observeOn(workerScheduler)
            .andThen(buildUseCaseCompletable())
            .observeOn(observerScheduler)
            .subscribeBy(
                    onComplete = onComplete,
                    onError = onError
            )
}

interface ResultUseCase<ReturnType, in Params> where ReturnType : Any {

    fun buildUseCaseSingle(params: Params): Single<ReturnType>

    fun getResult(
            params: Params,
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onSuccess: (ReturnType) -> Unit = {}
    ): Disposable = Completable.complete()
            .observeOn(workerScheduler)
            .andThen(buildUseCaseSingle(params))
            .observeOn(observerScheduler)
            .subscribeBy(
                    onSuccess = onSuccess,
                    onError = onError
            )
}

interface ResultUseCaseNoParams<ReturnType> where ReturnType : Any {

    fun buildUseCaseSingle(): Single<ReturnType>

    fun getResult(
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onSuccess: (ReturnType) -> Unit = {}
    ): Disposable = Completable.complete()
            .observeOn(workerScheduler)
            .andThen(buildUseCaseSingle())
            .observeOn(observerScheduler)
            .subscribeBy(
                    onSuccess = onSuccess,
                    onError = onError
            )
}

interface StreamUseCase<ReturnType, in Params> where ReturnType : Any {

    fun buildUseCaseObservable(params: Params): Observable<ReturnType>

    fun observeStream(
            params: Params,
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onNext: (ReturnType) -> Unit = {}
    ): Disposable = Completable.complete()
            .observeOn(workerScheduler)
            .andThen(buildUseCaseObservable(params))
            .observeOn(observerScheduler)
            .subscribeBy(
                    onNext = onNext,
                    onError = onError
            )

}

interface StreamUseCaseNoParams<ReturnType> where ReturnType : Any {

    fun buildUseCaseObservable(): Observable<ReturnType>

    fun observeStream(
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onNext: (ReturnType) -> Unit = {}
    ): Disposable = Completable.complete()
            .observeOn(workerScheduler)
            .andThen(buildUseCaseObservable())
            .observeOn(observerScheduler)
            .subscribeBy(
                    onNext = onNext,
                    onError = onError
            )

}


interface MassiveStreamUseCase<ReturnType, in Params> where ReturnType : Any {

    fun buildUseCaseFlowable(params: Params): Flowable<ReturnType>

    fun observeStream(
            params: Params,
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onNext: (ReturnType) -> Unit = {}
    ): Disposable = Completable.complete()
            .observeOn(workerScheduler)
            .andThen(buildUseCaseFlowable(params))
            .observeOn(observerScheduler)
            .subscribeBy(
                    onNext = onNext,
                    onError = onError
            )

}

interface MassiveStreamUseCaseNoParams<ReturnType> where ReturnType : Any {

    fun buildUseCaseFlowable(): Observable<ReturnType>

    fun observeStream(
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onNext: (ReturnType) -> Unit = {}
    ): Disposable = Completable.complete()
            .observeOn(workerScheduler)
            .andThen(buildUseCaseFlowable())
            .observeOn(observerScheduler)
            .subscribeBy(
                    onNext = onNext,
                    onError = onError
            )

}