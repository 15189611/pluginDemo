package plugin.charles.com.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * @author 17111980
 * @date 2017/12/13.
 * 插件apk 的信息
 */


public class DLPluginPackage {

    public DexClassLoader dexClassLoader;
    public Resources resources;
    public PackageInfo packageInfo;
    public AssetManager assetManager;
    public String packerName;

    public String defaultActivity;


    public DLPluginPackage(DexClassLoader dexClassLoader, Resources resources, PackageInfo packageInfo) {
        this.dexClassLoader = dexClassLoader;
        this.resources = resources;
        this.packageInfo = packageInfo;
        this.assetManager = resources.getAssets();
        this.packerName = packageInfo.packageName;

        parseDefaultActivityName();
    }

    private final String parseDefaultActivityName() {
        if (packageInfo.activities != null && packageInfo.activities.length > 0) {
            return packageInfo.activities[0].name;
        }
        return "";
    }

}
