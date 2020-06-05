package com.reactlibrary.Upgrade;

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

    public IntegrationManager(Context context, Version version) {
        this.context = context;
        if (version != null) {
            this.version = version;
        }
    }

    public void noApp() {
        NoAppApplication noAppApplication = new NoAppApplication(this.context);
        noAppApplication.startProcess();
    }

    public void setState() {
        System.out.println(version.status);
        switch (version.status) {
            case VERSION_REGULAR:
                if (isEdit()) {
                    if (!isPause()) {
                        ApplicationHelper applicationHelper = new ApplicationHelper(context);
                        Long current_version = VersionHelper.getInstance().getCurrentVersion(applicationHelper);
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
                startNoApplicationDataProcess();
                break;
            case NO_VERSION:
                System.out.println("Ekli Versiyon Bulunmuyor");
                startNoVersionProcess();
                break;
            case APPLICATION_ERROR:
                System.out.println("Aplikasyon Bilgilerine Ulaşılamıyor");
                startApplicationErrorProcess();
                break;
            case NO_PACKAGE:
                System.out.println("Aplikasyon için Ekli paket bulunmuyor");
                startNoPackageProcess();
                break;
            case APPLICATION_DELETE:
                System.out.println("Aplikasyon Kaldırılmış");
                startApplicationDeleteProcess();
                break;
            case NO_APPLICATION:
                System.out.println("Bu Bu uygulama IBB Tarafından Oluşturulmamış ve Akredite Edilmemiş. Lütfen Bu uygulşama için IBB tarafından verilen Aplikasyon ID sini Kullanın");
                startNoApplicationProcess();
                break;
            default:
                break;
        }

    }

    public void startUpgradeProcess(Long version_type) {
        if (version_type == CRITICAL) {
            CriticalUpgrade criticalUpgrade = new CriticalUpgrade(this.context, this.version);
            criticalUpgrade.startProcess();
        } else if (version_type == IMPORTANT) {
            ImportantUpgrade importantUpgrade = new ImportantUpgrade(this.context, this.version);
            importantUpgrade.startProcess();
        } else if (version_type == WARNING) {
            WarningUpgrade warningUpgrade = new WarningUpgrade(this.context, this.version);
            warningUpgrade.startProcess();
        } else if (version_type == LOWIMPORTANT) {
            lowImportantUpgrade lowImportantUpgrade = new lowImportantUpgrade(this.context, this.version);
            lowImportantUpgrade.startProcess();
        } else if (version_type == REALTIME) {
            RealTimeUpgrade realTimeUpgrade = new RealTimeUpgrade(this.context, this.version);
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
        Long current_version = VersionHelper.getInstance().getCurrentVersion(applicationHelper);
        System.out.println("Silinen Versiyon Numarası " + this.version.remove_version_number);
        System.out.println("Cihazın Versiyonu " + current_version);
        System.out.println("Önerilen Versiyon " + this.version.versionData.versionDetail.versionNumber);
        if (current_version == this.version.versionData.versionDetail.versionNumber) {
            System.out.println("Güncelleme Gerekli Değil");
        } else {
            if(current_version < this.version.versionData.versionDetail.versionNumber){
                this.version.status = VERSION_REGULAR;
                setState();
            }else{
                VersionDeleteApplication versionDeleteApplication = new VersionDeleteApplication(this.context, this.version);
                versionDeleteApplication.startProcess();
            }
        }
    }

    private void startNoApplicationDataProcess() {
        NoApplicationDataApplication noApplicationDataApplication = new NoApplicationDataApplication(this.context, this.version);
        noApplicationDataApplication.startProcess();
    }

    private void startNoVersionProcess() {
        NoVersionApplication noVersionApplication = new NoVersionApplication(this.context, this.version);
        noVersionApplication.startProcess();
    }

    private void startApplicationErrorProcess() {
        ApplicationErrorApplication applicationErrorApplication = new ApplicationErrorApplication(this.context, this.version);
        applicationErrorApplication.startProcess();
    }

    private void startNoPackageProcess() {
        NoPackageApplication noPackageApplication = new NoPackageApplication(this.context, this.version);
        noPackageApplication.startProcess();
    }

    private void startApplicationDeleteProcess() {
        ApplicationDeleteApplication aplicationDeleteApplication = new ApplicationDeleteApplication(this.context, this.version);
        aplicationDeleteApplication.startProcess();
    }

    private void startNoApplicationProcess() {
        NoAppApplication noAppApplication = new NoAppApplication(this.context);
        noAppApplication.startProcess();
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
