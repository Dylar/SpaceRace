package de.bitb.spacerace.usecase

import com.badlogic.gdx.Gdx
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
abstract class UseCase<Type, in Params>(
        private val workerScheduler: Scheduler = Schedulers.trampoline(),
        private val observerScheduler: Scheduler = Schedulers.trampoline()
) where Type : Any {

    protected val onNextStub: (Type) -> Unit = {}
    protected val onErrorStub: (Throwable) -> Unit = {}
    var observer: DisposableObserver<Type>? = null

    val defaultObserver = object : DisposableObserver<Type>() {
        override fun onComplete() {
            // nothing
        }

        override fun onNext(t: Type) {
            // nothing
        }

        override fun onError(e: Throwable) {
            // nothing
        }
    }

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    abstract fun buildUseCaseObservable(params: Params): Observable<Type>

    @JvmOverloads
    fun observe(params: Params, observer: DisposableObserver<Type> = defaultObserver): Disposable {

        this.observer = observer

        return buildUseCaseObservable(params)
                .subscribeOn(workerScheduler)
                .observeOn(observerScheduler)
                .doOnDispose { observer.onComplete() }
                .subscribeWith(observer)
    }

    /**
     * Invoke use case by declaring functions for onNext() or onError()
     */
    @JvmOverloads
    operator fun invoke(
            params: Params,
            onNext: (Type) -> Unit = onNextStub,
            onError: (Throwable) -> Unit = onErrorStub
    ): Disposable {

        val defaultObserver = object : DisposableObserver<Type>() {
            override fun onComplete() {
                // nothing
            }

            override fun onNext(t: Type) {
                Gdx.app.postRunnable { onNext(t) }
            }

            override fun onError(e: Throwable) {
                Gdx.app.postRunnable { onError(e) }
            }
        }

        return buildUseCaseObservable(params)
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline()) //TODO GdxSchedulers.mainThread
                .doOnDispose { defaultObserver.onComplete() }
                .subscribeWith(defaultObserver)
    }

    /**
     * Invoke use case by subscribing to it
     */
    operator fun invoke(params: Params): Observable<Type> {

        val defaultObserver = object : DisposableObserver<Type>() {
            override fun onComplete() {
                // nothing
            }

            override fun onNext(t: Type) {
                onNext(t)
            }

            override fun onError(e: Throwable) {
                onError(e)
            }
        }

        return buildUseCaseObservable(params)
                .subscribeOn(workerScheduler)
                .observeOn(observerScheduler)
                .doOnDispose { defaultObserver.onComplete() }
    }

    /**
     * Use class [None] for fire and forget [UseCase]
     */
    object None
}