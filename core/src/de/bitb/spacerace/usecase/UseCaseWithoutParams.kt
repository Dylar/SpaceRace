/*
 * Copyright (c) 2018 EDEKA AG
 *  All rights reserved.
 */

package digital.edeka.core.usecase

import de.bitb.spacerace.usecase.GdxSchedulers
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
abstract class UseCaseWithoutParams<Type>(
    workerScheduler: Scheduler = Schedulers.io(),
    observerScheduler: Scheduler = Schedulers.trampoline()
) : UseCase<Type, UseCase.None>(workerScheduler, observerScheduler)
        where Type : Any {

    override fun buildUseCaseObservable(params: None): Observable<Type> {
        return buildUseCaseObservable()
    }

    /**
     * Builds an [Observable] which will be used when executing the current [UseCaseWithoutParams].
     */
    abstract fun buildUseCaseObservable(): Observable<Type>

    @JvmOverloads
    fun observe(observer: DisposableObserver<Type> = defaultObserver): Disposable {
        return super.observe(None, observer)
    }

    /**
     * Invoke use case by declaring functions for onNext() or onError()
     */
    @JvmOverloads
    operator fun invoke(
        onNext: (Type) -> Unit = onNextStub,
        onError: (Throwable) -> Unit = onErrorStub
    ): Disposable {
        return super.invoke(None, onNext, onError)
    }

    /**
     * Invoke use case by subscribing to it
     */
    operator fun invoke(): Observable<Type> {
        return super.invoke(None)
    }

}