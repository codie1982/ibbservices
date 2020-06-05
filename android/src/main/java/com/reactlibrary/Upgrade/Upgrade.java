package com.reactlibrary.Upgrade;

import android.content.Context;

import com.reactlibrary.Models.Version;
import com.reactlibrary.Constain.VersionConstains;

import static com.reactlibrary.Constain.VersionConstains.*;

public class Upgrade {

    protected Version nVersion;
    protected Context context;

    //constractor
    public Upgrade() {
    }
    //constractor
    public Upgrade(Context context) {
        this.context = context;
    }
    //constractor
    public Upgrade(Context context ,Version version) {
        this.nVersion = version;
        this.context = context;
    }

    protected Version getVersionData(){
        return this.nVersion;
    }

    public Long getVersionType() {
        return nVersion.versionData.versionDetail.versionType;
    }


}
