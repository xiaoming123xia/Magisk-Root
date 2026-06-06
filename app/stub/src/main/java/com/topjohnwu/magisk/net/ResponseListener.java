package com.mobai.magisk.net;

public interface ResponseListener<T> {
    void onResponse(T response);
}
