package com.reactlibrary.Models;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Createdtime implements Serializable
{

    @SerializedName("_seconds")
    @Expose
    public Integer seconds;
    @SerializedName("_nanoseconds")
    @Expose
    public Integer nanoseconds;
    private final static long serialVersionUID = 3437950556808191528L;

}