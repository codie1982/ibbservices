package com.reactlibrary.Models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionData implements Serializable
{

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("version_detail")
    @Expose
    public VersionDetail versionDetail;
    private final static long serialVersionUID = -1138089488243560209L;

    public VersionData withId(String id) {
        this.id = id;
        return this;
    }

    public VersionData withVersionDetail(VersionDetail versionDetail) {
        this.versionDetail = versionDetail;
        return this;
    }

}
