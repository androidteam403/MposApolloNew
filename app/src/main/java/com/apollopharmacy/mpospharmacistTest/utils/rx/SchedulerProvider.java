package com.apollopharmacy.mpospharmacistTest.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by apple on 08/05/18.
 */


public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
