package plugin.charles.com.plugindemo.hook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.LinearLayout;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import plugin.charles.com.plugindemo.R;

/**
 * @author 17111980
 * @date 2017/12/12.
 */


public class PluginManager {
    private Context context;

    private static volatile PluginManager pluginManager;

    private PluginManager(Context context) {
        this.context = context;
    }

    public static PluginManager getInstance(Context context) {
        if (pluginManager == null) {
            synchronized (PluginManager.class) {
                if (pluginManager == null) {
                    pluginManager = new PluginManager(context);
                }
            }
        }
        return pluginManager;
    }

    //动态加载dex文件  这里是个jar 包里面的方法
    public void dexJar() {
        //dex解压释放后的目录
        final File dexOutPutDir = context.getDir("dex", 0);
        //dex所在目录
        final String dexPath = Environment.getExternalStorageDirectory().toString() + File.separator + "new_log.jar";

        //第一个参数：是dex压缩文件的路径
        //第二个参数：是dex解压缩后存放的目录
        //第三个参数：是C/C++依赖的本地库文件目录,可以为null
        //第四个参数：是上一级的类加载器
        DexClassLoader classLoader = new DexClassLoader(dexPath, dexOutPutDir.getAbsolutePath(), null, context.getClassLoader());

        try {
            final Class<?> loadClazz = classLoader.loadClass("plugin.charles.com.logutil.LogUitl");
            final Object o = loadClazz.newInstance();
            final Method printLogMethod = loadClazz.getDeclaredMethod("printLog");
            printLogMethod.setAccessible(true);
            printLogMethod.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //动态加载apk 然后调用里面的resours
    public void dynamicLoadApk(String pApkFilePath, String pApkPacketName, int apk_type, LinearLayout linearLayout) {
        File file = context.getDir("dex", Context.MODE_PRIVATE);
        //第一个参数：是dex压缩文件的路径
        //第二个参数：是dex解压缩后存放的目录
        //第三个参数：是C/C++依赖的本地库文件目录,可以为null
        //第四个参数：是上一级的类加载器
        DexClassLoader classLoader = new DexClassLoader(pApkFilePath, file.getAbsolutePath(), null, context.getClassLoader());
        try {
            final Class<?> loadClazz = classLoader.loadClass(pApkPacketName + ".R$drawable");
            //插件中皮肤的名称是skin_one
            final Field skinOneField = apk_type == 1 ? loadClazz.getDeclaredField("skin_one") : loadClazz.getDeclaredField("skin_two");
            skinOneField.setAccessible(true);
            //反射获取skin_one的resousreId
            final int resousreId = (int) skinOneField.get(R.id.class);
            //可以加载插件资源的Resources
            final Resources resources = createResources(pApkFilePath);
            if (resources != null) {
                final Drawable drawable = resources.getDrawable(resousreId);
                linearLayout.setBackgroundDrawable(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取AssetManager   用来加载插件资源
     *
     * @param pFilePath 插件的路径
     * @return
     */
    private AssetManager createAssetManager(String pFilePath) {
        try {
            final AssetManager assetManager = AssetManager.class.newInstance();
            final Class<?> assetManagerClazz = Class.forName("android.content.res.AssetManager");
            final Method addAssetPathMethod = assetManagerClazz.getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, pFilePath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //这个Resources就可以加载非宿主apk中的资源
    private Resources createResources(String pFilePath) {
        final AssetManager assetManager = createAssetManager(pFilePath);
        Resources superRes = context.getResources();
        return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }

    /**
     * 根据sdcard路径，获取未安装apk的信息
     *
     * @param context
     * @param pApkFilePath apk文件的path
     * @return
     */
    public String getUninstallApkPkgName(Context context, String pApkFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(pApkFilePath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            return appInfo.packageName;
        }
        return "";
    }

    private String dexOutputPath;

    public DexClassLoader createDexClassLoader(String dexPath) {
        File dexOutputDir = context.getDir("dex", Context.MODE_PRIVATE);
        dexOutputPath = dexOutputDir.getAbsolutePath();
        DexClassLoader loader = new DexClassLoader(dexPath, dexOutputPath, null, context.getClassLoader());
        return loader;
    }

}
