package com.reactlibrary.Models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedVersionTime implements Serializable
{

    @SerializedName("_seconds")
    @Expose
    public Integer seconds;
    @SerializedName("_nanoseconds")
    @Expose
    public Integer nanoseconds;
    private final static long serialVersionUID = -2024127150643003508L;

    public CreatedVersionTime withSeconds(Integer seconds) {
        this.seconds = seconds;
        return this;
    }

    public CreatedVersionTime withNanoseconds(Integer nanoseconds) {
        this.nanoseconds = nanoseconds;
        return this;
    }

}
