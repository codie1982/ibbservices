package com.reactlibrary.module;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ApplicationInfo {
    public ApplicationInfo(QueryDocumentSnapshot document) {
        String documentID = document.getId();
        String application_market = document.getString("application_market");
        String application_name = document.getString("application_name");
        String category = document.getString("category");
        String description = document.getString("description");
        String download_link = document.getString("download_link");
        String logo = document.getString("logo");
        String package_name = document.getString("package_name");
        String published = document.getString("published");
        String short_title = document.getString("short_title");
        String title = document.getString("title");
        String type = document.getString("type");
        Timestamp update_time = document.getTimestamp("update_time");
        Long published_version = document.getLong("version");

        setApplication_market(application_market);
        setApplication_name(application_name);
        setCategory(category);
        setDescription(description);
        setDownload_link(download_link);
        setLogo(logo);
        setPackage_name(package_name);
        setPublished(published);
        setShort_title(short_title);
        setTitle(title);
        setType(type);
        setUpdate_time(update_time);
        setPublished_version(published_version);

    }

    //Aplikasyona ait bilgileri almamın gerekiyor
    private String documentID;
    private String application_market;
    private String application_name;
    private String category;
    private String description;
    private String download_link;
    private String logo;

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getApplication_market() {
        return application_market;
    }

    public void setApplication_market(String application_market) {
        this.application_market = application_market;
    }

    public String getApplication_name() {
        return application_name;
    }

    public void setApplication_name(String application_name) {
        this.application_name = application_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownload_link() {
        return download_link;
    }

    public void setDownload_link(String download_link) {
        this.download_link = download_link;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public Long getPublished_version() {
        return published_version;
    }

    public void setPublished_version(Long published_version) {
        this.published_version = published_version;
    }

    public Long getCurrent_version() {
        return current_version;
    }

    public void setCurrent_version(Long current_version) {
        this.current_version = current_version;
    }

    private String package_name;
    private String published;
    private String short_title;
    private String title;
    private String type;
    private Timestamp update_time;
    private Long published_version;
    private Long current_version;
}


