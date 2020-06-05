package com.reactlibrary.Models;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicTime implements Serializable
{

    @SerializedName("_seconds")
    @Expose
    public long seconds;
    @SerializedName("_nanoseconds")
    @Expose
    public long nanoseconds;
    private final static long serialVersionUID = 6545045281400313723L;

    public PublicTime withSeconds(long seconds) {
        this.seconds = seconds;
        return this;
    }

    public PublicTime withNanoseconds(long nanoseconds) {
        this.nanoseconds = nanoseconds;
        return this;
    }

}