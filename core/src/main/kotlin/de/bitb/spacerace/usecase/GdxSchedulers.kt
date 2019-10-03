package de.bitb.spacerace.usecase

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.config.IS_TEST
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object GdxSchedulers {

    //    val objBoxThread: io.objectbox.reactive.Scheduler = object : io.objectbox.reactive.Scheduler{
//        override fun <T : Any?> run(runnable: RunWithParam<Any>, param: T) {
//            runnable.run(param)
//        }
////        override fun createWorker(): Worker {
////            return workerThread
////        }
//
//    }
    //    val mainThread: Scheduler = Schedulers.trampoline()
    val mainThread = object : Scheduler() {
        override fun createWorker(): Worker {
            return object : Worker() {
                override fun isDisposed(): Boolean {
                    return true
                }

                override fun schedule(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    if (IS_TEST) {
                        run.run()
                    } else {
                        Gdx.app.postRunnable(run)
                    }
                    return this
                }

                override fun dispose() {

                }

            }
        }
    }

    val workerThread = if (IS_TEST) mainThread else Schedulers.from(Executors.newFixedThreadPool(1))
}
