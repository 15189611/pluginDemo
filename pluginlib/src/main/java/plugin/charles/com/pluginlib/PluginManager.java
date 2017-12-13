package plugin.charles.com.pluginlib;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

/**
 * @author 17111980
 * @date 2017/12/13.
 */


public class PluginManager {

    private static final String TAG = "Charles";

    /**
     * return value of {@link #startPluginActivity(Activity, DLIntent)} start
     * success
     */
    public static final int START_RESULT_SUCCESS = 0;

    /**
     * return value of {@link #startPluginActivity(Activity, DLIntent)} package
     * not found
     */
    public static final int START_RESULT_NO_PKG = 1;

    /**
     * return value of {@link #startPluginActivity(Activity, DLIntent)} class
     * not found
     */
    public static final int START_RESULT_NO_CLASS = 2;

    /**
     * return value of {@link #startPluginActivity(Activity, DLIntent)} class
     * type error
     */
    public static final int START_RESULT_TYPE_ERROR = 3;

    private static volatile PluginManager pluginManager;
    private int mFrom = DLConstants.FROM_INTERNAL;
    private Context mContext;

    /**
     * 用来保存plugin apk包的信息
     */
    private final HashMap<String, DLPluginPackage> mPackagesHolder = new HashMap<String, DLPluginPackage>();

    private PluginManager(Context context) {
        this.mContext = context;
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

    public DLPluginPackage loadApk(String dexPath) {
        mFrom = DLConstants.FROM_EXTERNAL;
        PackageInfo packageInfo = getUninstallApkInfo(mContext, dexPath);

        if (packageInfo == null) {
            return null;
        }
        //解析plugin 信息
        DLPluginPackage pluginPackage = preparePluginEnv(packageInfo, dexPath);
        return pluginPackage;

    }

    private DLPluginPackage preparePluginEnv(PackageInfo packageInfo, String dexPath) {
        String packageName = packageInfo.packageName;
        DLPluginPackage dlPluginPackage = mPackagesHolder.get(packageName);

        if (dlPluginPackage != null) {
            return dlPluginPackage;
        }

        //创建dexClassLoader
        DexClassLoader dexClassLoader = createDexClassLoader(dexPath);
        //创建assetManager
        AssetManager assetManager = createAssetManager(dexPath);
        //创建resources
        Resources resources = createResources(assetManager);

        DLPluginPackage pluginPackage = new DLPluginPackage(dexClassLoader, resources, packageInfo);

        mPackagesHolder.put(packageName, pluginPackage);
        return pluginPackage;
    }

    private Resources createResources(AssetManager assetManager) {
        Resources superRes = mContext.getResources();
        return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }

    private AssetManager createAssetManager(String dexPath) {
        try {
            final AssetManager assetManager = AssetManager.class.newInstance();
            final Method addAssetPathMethod = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, dexPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String dexOutputPath;

    private DexClassLoader createDexClassLoader(String dexPath) {
        File dexOutputDir = mContext.getDir("dex", Context.MODE_PRIVATE);
        dexOutputPath = dexOutputDir.getAbsolutePath();
        DexClassLoader loader = new DexClassLoader(dexPath, dexOutputPath, null, mContext.getClassLoader());
        return loader;
    }

    /**
     * 根据sdcard路径，获取未安装apk的信息
     */
    public PackageInfo getUninstallApkInfo(Context context, String pApkFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(pApkFilePath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            return pkgInfo;
        }
        return null;
    }

    public int startPluginActivity(Context context, DLIntent dlIntent) {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            dlIntent.setClassName(context, dlIntent.getPluginClass());
            performStartActivityForResult(context, dlIntent);
            return PluginManager.START_RESULT_SUCCESS;
        }

        String packageName = dlIntent.getPluginPackage();
        if (TextUtils.isEmpty(packageName)) {
            throw new NullPointerException("null packerName");
        }
        //获取插件apk 对应的信息
        DLPluginPackage pluginPackage = mPackagesHolder.get(packageName);
        if (pluginPackage == null) {
            return START_RESULT_NO_PKG;
        }

        //获取要启动的插件Activity 全路径
        final String className = getPluginActivityFullPath(dlIntent, pluginPackage);
        //加载插件的Class
        Class<?> clazz = loadPluginClass(pluginPackage.dexClassLoader, className);
        if (clazz == null) {
            return START_RESULT_NO_CLASS;
        }

        //获取代理Activity 来代替插件Activity启动
        Class<? extends Activity> activityClass = getProxyActivityClass(clazz);
        if (activityClass == null) {
            return START_RESULT_TYPE_ERROR;
        }

        // put extra data
        dlIntent.putExtra(DLConstants.EXTRA_CLASS, className);
        dlIntent.putExtra(DLConstants.EXTRA_PACKAGE, packageName);
        dlIntent.setClass(mContext, activityClass);
        //最后启动用代理Activity来启动
        performStartActivityForResult(context, dlIntent);
        return START_RESULT_SUCCESS;
    }

    private void performStartActivityForResult(Context context, DLIntent dlIntent) {
        Log.d(TAG, "launch " + dlIntent.getPluginClass());
        context.startActivity(dlIntent);
    }

    private String getPluginActivityFullPath(DLIntent dlIntent, DLPluginPackage pluginPackage) {
        String className = dlIntent.getPluginClass();
        className = (className == null ? pluginPackage.defaultActivity : className);
        if (className.startsWith(".")) {
            className = dlIntent.getPluginPackage() + className;
        }
        return className;
    }

    private Class<?> loadPluginClass(ClassLoader classLoader, String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clazz;
    }

    private Class<? extends Activity> getProxyActivityClass(Class<?> clazz) {
        Class<? extends Activity> activityClass = null;
        if (DLBasePluginActivity.class.isAssignableFrom(clazz)) {
            activityClass = DLProxyActivity.class;
        }

        return activityClass;
    }

    public DLPluginPackage getPluginPackage(String packageName) {
        return mPackagesHolder.get(packageName);
    }

}
