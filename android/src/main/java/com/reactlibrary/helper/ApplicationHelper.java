package com.reactlibrary.Helper;

import android.content.Context;
import android.content.pm.PackageInfo;

public class ApplicationHelper {
    Context context;

    public ApplicationHelper(Context context) {
        this.context = context;
    }

    public PackageInfo getPackageInfo() throws Exception {
        return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0);
    }
}
