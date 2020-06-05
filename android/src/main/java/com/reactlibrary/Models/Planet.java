package com.reactlibrary.Models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Planet implements Serializable
{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("information")
    @Expose
    public Information information;
    private final static long serialVersionUID = 3453148118227818733L;

}