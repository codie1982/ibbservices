package com.reactlibrary.Models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Version implements Serializable
{
    //remove_version_number

    @SerializedName("remove_version_number")
    @Expose
    public Long remove_version_number;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("status")
    @Expose
    public String status;


    @SerializedName("success")
    @Expose
    public boolean success;

    @SerializedName("version_data")
    @Expose
    public VersionData versionData;
    private final static long serialVersionUID = -4735213553878236093L;

    public Version withRemoveVersionNumber(Long remove_version_number) {
        this.remove_version_number = remove_version_number;
        return this;
    }

    public Version withSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Version withStatus(String status) {
        this.status = status;
        return this;
    }

    public Version withMessage(String message) {
        this.message = message;
        return this;
    }

    public Version withVersionInfo(VersionData versionData) {
        System.out.println(versionData);
        this.versionData = versionData;
        return this;
    }

}
