package com.behruz.magmovie.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mishael.harry on 3/30/2018.
 */

public class BaseResponse<T> {

    @SerializedName("Succeeded")
    private boolean succeeded;
    @SerializedName("Message")
    private String message;
    @SerializedName("Result")
    private T result;

    public boolean isSucceeded() {
        return succeeded;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}
