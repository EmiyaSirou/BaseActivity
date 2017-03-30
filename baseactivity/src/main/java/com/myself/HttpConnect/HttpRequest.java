package com.myself.HttpConnect;

import rx.Observable;

/**
 * Created by Emiya on 16/9/2.
 */
public class HttpRequest {
    private String identify;
    private Observable observable;

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public Observable getObservable() {
        return observable;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }
}
