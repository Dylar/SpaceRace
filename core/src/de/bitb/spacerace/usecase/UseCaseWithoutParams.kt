package de.bitb.spacerace.usecase

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
abstract class UseCaseWithoutParams<ReturnType>(
        workerScheduler: Scheduler = GdxSchedulers.workerThread,
        observerScheduler: Scheduler = GdxSchedulers.mainThread
) : UseCase<ReturnType, None>(workerScheduler, observerScheduler)
        where ReturnType : Any {

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    abstract fun buildUseCaseObservable(): Observable<ReturnType>

    override fun buildUseCaseObservable(params: None): Observable<ReturnType> =
            buildUseCaseObservable()

    /**
     * Builds an [Observable] build from [buildUseCaseObservable] as [Single]
     */
    fun buildUseCaseSingle(): Single<ReturnType> =
            buildUseCaseSingle(None)

    /**
     * Builds an [Observable] build from [buildUseCaseObservable] as [Completable]
     */
    fun buildUseCaseCompletable(): Completable =
            buildUseCaseCompletable(None)

    /**
     * Subscribe to an [Observable] build from [buildUseCaseObservable]
     */
    fun observeStream(
            onNext: (ReturnType) -> Unit = onSuccessStub,
            onError: (Throwable) -> Unit = onErrorStub
    ): Disposable = buildUseCaseObservable()
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
            onSuccess: (ReturnType) -> Unit = onSuccessStub,
            onError: (Throwable) -> Unit = onErrorStub
    ): Disposable = buildUseCaseSingle()
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
            onComplete: () -> Unit = onCompleteStub,
            onError: (Throwable) -> Unit = onErrorStub
    ): Disposable = buildUseCaseCompletable()
            .subscribeOn(workerScheduler)
            .observeOn(observerScheduler)
            .subscribeBy(
                    onComplete = onComplete,
                    onError = onError
            )
}

/**
 * Use class [None] for fire and forget [UseCase]
 */
object None