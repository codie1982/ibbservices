package com.reactlibrary.Models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionDetail implements Serializable
{

    @SerializedName("edit")
    @Expose
    public long edit;
    @SerializedName("version_string")
    @Expose
    public String versionString;
    @SerializedName("created_version_time")
    @Expose
    public CreatedVersionTime createdVersionTime;
    @SerializedName("version_title")
    @Expose
    public String versionTitle;
    @SerializedName("version_number")
    @Expose
    public Long versionNumber;
    @SerializedName("public")
    @Expose
    public long _public;
    @SerializedName("version_notes")
    @Expose
    public String versionNotes;
    @SerializedName("delete")
    @Expose
    public long delete;
    @SerializedName("pause")
    @Expose
    public long pause;

    @SerializedName("version_type")
    @Expose
    public Long versionType;

    @SerializedName("public_time")
    @Expose
    public PublicTime publicTime;
    @SerializedName("connection_state")
    @Expose
    public List<String> connectionState = null;
    @SerializedName("battery_state")
    @Expose
    public long batteryState;
    @SerializedName("admin_public")
    @Expose
    public long adminPublic;
    private final static long serialVersionUID = 6348980309672034884L;
    public String upload_url;

    public VersionDetail withEdit(long edit) {
        this.edit = edit;
        return this;
    }
    public VersionDetail withUploadUrl(String uploadUrl) {
        this.upload_url = uploadUrl;
        return this;
    }

    public VersionDetail withVersionString(String versionString) {
        this.versionString = versionString;
        return this;
    }

    public VersionDetail withCreatedVersionTime(CreatedVersionTime createdVersionTime) {
        this.createdVersionTime = createdVersionTime;
        return this;
    }

    public VersionDetail withVersionTitle(String versionTitle) {
        this.versionTitle = versionTitle;
        return this;
    }

    public VersionDetail withVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
        return this;
    }

    public VersionDetail withPublic(long _public) {
        this._public = _public;
        return this;
    }

    public VersionDetail withVersionNotes(String versionNotes) {
        this.versionNotes = versionNotes;
        return this;
    }
    public VersionDetail withPause(long pause) {
        this.pause = pause;
        return this;
    }

    public VersionDetail withDelete(long delete) {
        this.delete = delete;
        return this;
    }

    public VersionDetail withVersionType(Long versionType) {
        this.versionType = versionType;
        return this;
    }

    public VersionDetail withPublicTime(PublicTime publicTime) {
        this.publicTime = publicTime;
        return this;
    }

    public VersionDetail withConnectionState(List<String> connectionState) {
        this.connectionState = connectionState;
        return this;
    }

    public VersionDetail withBatteryState(long batteryState) {
        this.batteryState = batteryState;
        return this;
    }

    public VersionDetail withAdminPublic(long adminPublic) {
        this.adminPublic = adminPublic;
        return this;
    }

}