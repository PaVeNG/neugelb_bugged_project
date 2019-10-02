package com.bugged.themoviedb.util;

import rx.Subscription;

public class RxUtil {

    public static String token = "";

    public static void unsubscribe(Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String t) {
        token = t;
    }
}