package com.cyd.commonfileutil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.cyd.bean.Appinfo;

import java.util.ArrayList;
import java.util.List;

public class PackageUtil {


    public static List<Appinfo> getAppInfo(Context con) {
        return getAppInfo(con, (byte) 0x01);
    }

    /**
     * 获取已安装apk的具体信息，包含
     *
     * @param con  上下文对象，一般建议使用Appliction对象
     * @param flag 0x01:获取所有安装app的信息,0x02:只获取第三方的,0x03:只获取系统的
     * @return 包含具体apk信息的ArrayList
     */
    public static List<Appinfo> getAppInfo(Context con, byte flag) {
        List<Appinfo> ar = new ArrayList<>();
        PackageManager manager = con.getPackageManager();
        List<PackageInfo> packageInfos = manager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            boolean ifSave = true;
            if (flag != 0x01) {
                boolean ifVal = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0; //true则为第三方应用
                ifSave = ((flag == 0x02) && ifVal) || ((flag == 0x03) && !ifVal);
            }
            if (ifSave) {
                Appinfo info = new Appinfo();
                ApplicationInfo aInfo = packageInfo.applicationInfo;
                info.setAppName(aInfo.loadLabel(manager).toString());
                info.setPackageName(packageInfo.packageName);
                info.setVersionName(packageInfo.versionName);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)  //兼容8.0的方法
                    info.setVersionCode(packageInfo.getLongVersionCode());
                else
                    info.setVersionCode(packageInfo.versionCode);
                info.setIcon(aInfo.loadIcon(manager));
                info.setBanner(aInfo.loadBanner(manager));
                info.setLogo(aInfo.loadLogo(manager));
                ar.add(info);
            }
        }
        return ar;
    }
}
