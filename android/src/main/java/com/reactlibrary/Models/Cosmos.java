package com.reactlibrary.Models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cosmos implements Serializable
{


    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("snapshots")
    @Expose
    public Integer snapshots;
    @SerializedName("planet")
    @Expose
    public List<Planet> planet = null;
    private final static long serialVersionUID = -4543177614181372708L;

}