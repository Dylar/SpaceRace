package de.bitb.spacerace.core.utils

import de.bitb.spacerace.usecase.GdxSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function3

object RXFunctions {

    inline fun <T1, T2, R> zipParallel(
            s1: Single<T1>,
            s2: Single<T2>,
            scheduler1: Scheduler = GdxSchedulers.workerThread,
            scheduler2: Scheduler = GdxSchedulers.workerThread,
            crossinline zipper: (T1, T2) -> R
    ): Single<R> =
        Single.zip(
            s1.subscribeOn(scheduler1),
            s2.subscribeOn(scheduler2),
            BiFunction { t, u -> zipper.invoke(t, u) })

    inline fun <T1, T2, T3, R> zipParallel(
        s1: Single<T1>,
        s2: Single<T2>,
        s3: Single<T3>,
        scheduler1: Scheduler = GdxSchedulers.workerThread,
        scheduler2: Scheduler = GdxSchedulers.workerThread,
        scheduler3: Scheduler = GdxSchedulers.workerThread,
        crossinline zipper: (T1, T2, T3) -> R
    ): Single<R> {
        return Single.zip(
            s1.subscribeOn(scheduler1),
            s2.subscribeOn(scheduler2),
            s3.subscribeOn(scheduler3),
            Function3<T1, T2, T3, R> { t1, t2, t3 -> zipper.invoke(t1, t2, t3) }
        )
    }



//    inline fun <T1, T2, R> combineLAstParallel(
//            s1: Single<T1>,
//            s2: Single<T2>,
//            scheduler1: Scheduler = GdxSchedulers.workerThread,
//            scheduler2: Scheduler = GdxSchedulers.workerThread,
//            crossinline zipper: (T1, T2) -> R
//    ): Single<R> =
//            Single.com(
//                    s1.subscribeOn(scheduler1),
//                    s2.subscribeOn(scheduler2),
//                    BiFunction { t, u -> zipper.invoke(t, u) })

}
