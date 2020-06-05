package com.reactlibrary.Models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Information implements Serializable
{

    @SerializedName("application_short_title")
    @Expose
    public String applicationShortTitle;
    @SerializedName("createdtime")
    @Expose
    public Createdtime createdtime;
    @SerializedName("publisher")
    @Expose
    public String publisher;
    @SerializedName("download_count")
    @Expose
    public Integer downloadCount;
    @SerializedName("downloadLogoUrl")
    @Expose
    public String downloadLogoUrl;
    @SerializedName("packages")
    @Expose
    public List<String> packages = null;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("application_name")
    @Expose
    public String applicationName;
    @SerializedName("application_title")
    @Expose
    public String applicationTitle;
    @SerializedName("last_version")
    @Expose
    public String lastVersion;
    @SerializedName("application_description")
    @Expose
    public String applicationDescription;
    @SerializedName("category")
    @Expose
    public String category;
    private final static long serialVersionUID = -3944096029473104827L;

}