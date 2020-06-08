package com.reactlibrary.Models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApplicationData implements Serializable
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
    @SerializedName("application_delete")
    @Expose
    public long applicationDelete;
    @SerializedName("download_count")
    @Expose
    public long downloadCount;
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
    private final static long serialVersionUID = -8854276186455112611L;

    public ApplicationData withApplicationShortTitle(String applicationShortTitle) {
        this.applicationShortTitle = applicationShortTitle;
        return this;
    }

    public ApplicationData withCreatedtime(Createdtime createdtime) {
        this.createdtime = createdtime;
        return this;
    }

    public ApplicationData withPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public ApplicationData withApplicationDelete(long applicationDelete) {
        this.applicationDelete = applicationDelete;
        return this;
    }

    public ApplicationData withDownloadCount(long downloadCount) {
        this.downloadCount = downloadCount;
        return this;
    }

    public ApplicationData withDownloadLogoUrl(String downloadLogoUrl) {
        this.downloadLogoUrl = downloadLogoUrl;
        return this;
    }

    public ApplicationData withPackages(List<String> packages) {
        this.packages = packages;
        return this;
    }

    public ApplicationData withType(String type) {
        this.type = type;
        return this;
    }

    public ApplicationData withApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public ApplicationData withApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
        return this;
    }

    public ApplicationData withLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
        return this;
    }

    public ApplicationData withApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
        return this;
    }

    public ApplicationData withCategory(String category) {
        this.category = category;
        return this;
    }

}
