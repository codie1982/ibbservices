package com.reactlibrary.Upgrade;

import android.app.Activity;
import android.content.Context;

import com.reactlibrary.Helper.ApplicationHelper;
import com.reactlibrary.Helper.VersionHelper;
import com.reactlibrary.Models.Version;

import static com.reactlibrary.Constain.ApplicationConstains.*;
import static com.reactlibrary.Constain.VersionConstains.*;
import static com.reactlibrary.Constain.StatusConstains.*;


public class IntegrationManager {
    private Context context;
    private Version version;
    private Activity currnet_activity;
    private Long current_version;
    private String current_version_string;

    public IntegrationManager(Context context, Version version) {
        this.context = context;
        if (version != null) {
            this.version = version;
        }
    }

    public void setCurrnet_activity(Activity currnet_activity) {
        this.currnet_activity = currnet_activity;
    }

    public void noAppProcess() {
        NoApplication noApplication = new NoApplication(this.context);
        noApplication.startProcess();
    }

    public void setState() {
        System.out.println(version.status);
        switch (version.status) {
            case VERSION_REGULAR:
                if (isEdit()) {
                    if (!isPause()) {
                        ApplicationHelper applicationHelper = new ApplicationHelper(context);
                        current_version = VersionHelper.getInstance().getCurrentVersion(applicationHelper);
                        current_version_string = VersionHelper.getInstance().getCurrentVersionString(applicationHelper);
                        if (VersionHelper.getInstance().checkVersion(current_version, version.versionData.versionDetail.versionNumber)) {
                            System.out.println("Güncelleme Gerekli");
                            if (AUTO_UPGRADE) {
                                startUpgradeProcess(version.versionData.versionDetail.versionType);
                            }
                        } else {
                            System.out.println("Güncelleme Yok");
                            return;
                        }

                    } else {
                        //Version Durdurulmuş
                        System.out.println("Verisyon Durdurulmuş");
                        startPauseProcess();
                        return;
                    }
                } else {
                    //Version Düzenlenmemiş
                    System.out.println("Verisyon Düzenlenmemiş");
                    startNoEditProcess();
                    return;
                }
                break;
            case VERSION_DELETE:
                System.out.println("Verisyon Kaldırılmış");
                startVersionDeleteProcess();
                break;
            case NO_APPLICATION_DATA:
                System.out.println("Aplikasyon Bilgisi Bulunmuyor");
                startNoAppProcess();
                break;
            case NO_VERSION:
                System.out.println("Ekli Versiyon Bulunmuyor");
                startNoAppProcess();
                break;
            case APPLICATION_ERROR:
                System.out.println("Aplikasyon Bilgilerine Ulaşılamıyor");
                startNoAppProcess();
                break;
            case NO_PACKAGE:
                System.out.println("Aplikasyon için Ekli paket bulunmuyor");
                startNoAppProcess();
                break;
            case APPLICATION_DELETE:
                System.out.println("Aplikasyon Kaldırılmış");
                startNoAppProcess();
                break;
            case NO_APPLICATION:
                System.out.println("Bu Bu uygulama IBB Tarafından Oluşturulmamış ve Akredite Edilmemiş. Lütfen Bu uygulşama için IBB tarafından verilen Aplikasyon ID sini Kullanın");
                startNoAppProcess();
                break;
            default:
                break;
        }

    }

    public void startUpgradeProcess(Long version_type) {
        if (version_type == CRITICAL) {
            CriticalUpgrade criticalUpgrade = new CriticalUpgrade(this.context, this.version);
            criticalUpgrade.setCurrentVersion(current_version);
            criticalUpgrade.setCurrentVersionString(current_version_string);
            criticalUpgrade.setActivity(currnet_activity);
            criticalUpgrade.startProcess();
        } else if (version_type == IMPORTANT) {
            ImportantUpgrade importantUpgrade = new ImportantUpgrade(this.context, this.version);
            importantUpgrade.setCurrentVersion(current_version);
            importantUpgrade.setCurrentVersionString(current_version_string);
            importantUpgrade.setActivity(currnet_activity);
            importantUpgrade.startProcess();
        } else if (version_type == WARNING) {
            WarningUpgrade warningUpgrade = new WarningUpgrade(this.context, this.version);
            warningUpgrade.setCurrentVersion(current_version);
            warningUpgrade.setCurrentVersionString(current_version_string);
            warningUpgrade.startProcess();
        } else if (version_type == LOWIMPORTANT) {
            lowImportantUpgrade lowImportantUpgrade = new lowImportantUpgrade(this.context, this.version);
            lowImportantUpgrade.setCurrentVersion(current_version);
            lowImportantUpgrade.setCurrentVersionString(current_version_string);
            lowImportantUpgrade.startProcess();
        } else if (version_type == REALTIME) {
            RealTimeUpgrade realTimeUpgrade = new RealTimeUpgrade(this.context, this.version);
            realTimeUpgrade.setCurrentVersion(current_version);
            realTimeUpgrade.setCurrentVersionString(current_version_string);
            realTimeUpgrade.startProcess();
        } else {
            NoTypicalUpgrade noTypicalUpgrade = new NoTypicalUpgrade(this.context, this.version);
            noTypicalUpgrade.startProcess();
        }
    }

    private void startNoEditProcess() {
        NoEditApplication noEditApplication = new NoEditApplication(this.context, this.version);
        noEditApplication.startProcess();
    }

    private void startPauseProcess() {
        PauseApplication pauseApplication = new PauseApplication(this.context, this.version);
        pauseApplication.startProcess();
    }

    private void startVersionDeleteProcess() {
        ApplicationHelper applicationHelper = new ApplicationHelper(context);
        current_version = VersionHelper.getInstance().getCurrentVersion(applicationHelper);
        current_version_string = VersionHelper.getInstance().getCurrentVersionString(applicationHelper);

        System.out.println("Silinen Versiyon Numarası " + this.version.remove_version_number);
        System.out.println("Cihazın Versiyonu " + current_version);
        System.out.println("Önerilen Versiyon " + this.version.versionData.versionDetail.versionNumber);

        if (current_version == this.version.versionData.versionDetail.versionNumber) {
            System.out.println("Güncelleme Gerekli Değil");
        } else {
            if (current_version < this.version.versionData.versionDetail.versionNumber) {
                this.version.status = VERSION_REGULAR;
                setState();
            } else {
                startUpgradeProcess(IMPORTANT);
            }
        }
    }

    private void startNoAppProcess() {
        NoApplication noApplication = new NoApplication(this.context, this.version);
        noApplication.startProcess();
    }


    private boolean isEdit() {
        if (version.versionData.versionDetail.edit == 1) {
            return true;
        } else {
            return false;
        }
    }


    private boolean isPause() {
        if (version.versionData.versionDetail.pause == 1) {
            return true;
        } else {
            return false;
        }
    }
}
