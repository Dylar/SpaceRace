package de.bitb.spacerace.usecase

import com.badlogic.gdx.Gdx
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

//TODO
object GdxSchedulers {
    //    val mainThread: Scheduler = Schedulers.trampoline()
    val mainThread = object : Scheduler() {
        override fun createWorker(): Worker {
            return object : Worker() {
                override fun isDisposed(): Boolean {
                    return true
                }

                override fun schedule(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    Gdx.app.postRunnable(run)
                    return this
                }

                override fun dispose() {

                }

            }
        }
    }

    val workerThread = Schedulers.from(Executors.newFixedThreadPool(1))
}
