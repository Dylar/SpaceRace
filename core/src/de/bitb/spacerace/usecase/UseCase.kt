package de.bitb.spacerace.usecase

import de.bitb.spacerace.Logger
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
abstract class UseCase<ReturnType, in Params>(
        protected val workerScheduler: Scheduler = Schedulers.io(),
        protected val observerScheduler: Scheduler = GdxSchedulers.mainThread
) where ReturnType : Any {
    private val onCompleteStub: () -> Unit =
            { Logger.println("Default onComplete") }
    private val onSuccessStub: (ReturnType) -> Unit =
            { Logger.println("Default onSuccess: $it") }
    private val onErrorStub: (Throwable) -> Unit =
            { Logger.println("Default onError: $it") }

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    abstract fun buildUseCaseObservable(params: Params): Observable<ReturnType>

    /**
     * Builds an [Observable] build from [buildUseCaseObservable] as [Single]
     */
    fun buildUseCaseSingle(params: Params): Single<ReturnType> = Single
            .fromObservable<ReturnType>(buildUseCaseObservable(params))

    /**
     * Builds an [Observable] build from [buildUseCaseObservable] as [Completable]
     */
    fun buildUseCaseCompletable(params: Params): Completable = Completable
            .fromObservable<ReturnType>(buildUseCaseObservable(params))

    /**
     * Subscribe to an [Observable] build from [buildUseCaseObservable]
     */
    fun observeStream(
            params: Params,
            onNext: (ReturnType) -> Unit = onSuccessStub,
            onError: (Throwable) -> Unit = onErrorStub
    ): Disposable = buildUseCaseObservable(params)
            .subscribeOn(workerScheduler)
            .observeOn(observerScheduler)
            .subscribeBy(
                    onNext = onNext,
                    onError = onError
            )

    /**
     * Subscribe to an [Single] (converted from [buildUseCaseObservable])
     */
    fun getResult(
            params: Params,
            onSuccess: (ReturnType) -> Unit = onSuccessStub,
            onError: (Throwable) -> Unit = onErrorStub
    ): Disposable = buildUseCaseSingle(params)
            .subscribeOn(workerScheduler)
            .observeOn(observerScheduler)
            .subscribeBy(
                    onSuccess = onSuccess,
                    onError = onError
            )

    /**
     * Subscribe to an [Completable] (converted from [buildUseCaseObservable])
     */
    fun execute(
            params: Params,
            onComplete: () -> Unit = onCompleteStub,
            onError: (Throwable) -> Unit = onErrorStub
    ): Disposable = buildUseCaseCompletable(params)
            .subscribeOn(workerScheduler)
            .observeOn(observerScheduler)
            .subscribeBy(
                    onComplete = onComplete,
                    onError = onError
            )
}