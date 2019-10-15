package com.shiftweather.presentation

import io.reactivex.Scheduler
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class RxSchedulersOverrideRule : TestRule {

    private val rxJavaImmediateScheduler =
        Function<Scheduler, Scheduler> { Schedulers.trampoline() }

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(rxJavaImmediateScheduler)
                RxJavaPlugins.setNewThreadSchedulerHandler(rxJavaImmediateScheduler)

                base.evaluate()

                RxJavaPlugins.reset()
            }
        }
}