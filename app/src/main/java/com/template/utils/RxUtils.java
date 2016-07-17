package com.template.utils;

import java.util.ArrayList;

import rx.Subscription;
import timber.log.Timber;

public class RxUtils {

    public static void onError(Throwable throwable) {

        Timber.e(throwable, "Error");

    }

    public static void safeUnsubscribe(ArrayList<Subscription> subscriptions) {

        for (Subscription subscription : subscriptions) {

            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }

        }

    }
}

