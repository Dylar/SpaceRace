package de.bitb.spacerace.usecase.core

import de.bitb.spacerace.Logger
import de.bitb.spacerace.usecase.GdxSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

val defaultWorkerThread = GdxSchedulers.workerThread
val defaultSubscriberThread = GdxSchedulers.mainThread
val defaultOnError: (Throwable) -> Unit = {
    Logger.println("Default onError:")
    it.printStackTrace()
}

interface ExecuteUseCase<in Params> {

    fun buildUseCaseCompletable(params: Params): Completable

    fun execute(
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            params: Params,
            onError: (Throwable) -> Unit = defaultOnError,
            onComplete: () -> Unit = {}
    ): Disposable = buildUseCaseCompletable(params)
            .subscribeOn(workerScheduler)
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
    ): Disposable = buildUseCaseCompletable()
            .subscribeOn(workerScheduler)
            .observeOn(observerScheduler)
            .subscribeBy(
                    onComplete = onComplete,
                    onError = onError
            )
}

interface ResultUseCase<ReturnType, in Params> where ReturnType : Any {

    fun buildUseCaseSingle(params: Params): Single<ReturnType>

    fun getResult(
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            params: Params,
            onError: (Throwable) -> Unit = defaultOnError,
            onSuccess: (ReturnType) -> Unit = {}
    ): Disposable = buildUseCaseSingle(params)
            .subscribeOn(workerScheduler)
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
    ): Disposable = buildUseCaseSingle()
            .subscribeOn(workerScheduler)
            .observeOn(observerScheduler)
            .subscribeBy(
                    onSuccess = onSuccess,
                    onError = onError
            )
}

interface StreamUseCase<ReturnType, in Params> where ReturnType : Any {

    fun buildUseCaseFlowable(params: Params): Observable<ReturnType>

    fun observeStream(
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            params: Params,
            onError: (Throwable) -> Unit = defaultOnError,
            onNext: (ReturnType) -> Unit = {}
    ): Disposable = buildUseCaseFlowable(params)
            .subscribeOn(workerScheduler)
            .observeOn(observerScheduler)
            .subscribeBy(
                    onNext = onNext,
                    onError = onError
            )

}

interface StreamUseCaseNoParams<ReturnType> where ReturnType : Any {

    fun buildUseCaseFlowable(): Observable<ReturnType>

    fun observeStream(
            workerScheduler: Scheduler = defaultWorkerThread,
            observerScheduler: Scheduler = defaultSubscriberThread,
            onError: (Throwable) -> Unit = defaultOnError,
            onNext: (ReturnType) -> Unit = {}
    ): Disposable = buildUseCaseFlowable()
            .subscribeOn(workerScheduler)
            .observeOn(observerScheduler)
            .subscribeBy(
                    onNext = onNext,
                    onError = onError
            )

}