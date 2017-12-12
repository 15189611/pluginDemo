package plugin.charles.com.plugindemo.hook;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Constructor;

import plugin.charles.com.plugindemo.plugin.DLAttachable;
import plugin.charles.com.plugindemo.plugin.DLPlugin;
import plugin.charles.com.plugindemo.util.DLConstants;

/**
 * @author 17111980
 * @date 2017/12/11.
 */


public class DLProxyImpl {
    private Activity mProxyActivity;
    private String mPackageName;
    private String mClass;
    protected DLPlugin mPluginActivity;

    public DLProxyImpl(Activity activity) {
        mProxyActivity = activity;
    }

    public void onCreate(Intent intent) {
        if (intent != null) {
            mPackageName = intent.getStringExtra(DLConstants.EXTRA_PACKAGE);
            mClass = intent.getStringExtra(DLConstants.EXTRA_CLASS);
            Log.e("Charles2", "mPackageName==" + mPackageName + "----mClass==" + mClass);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    protected void launchTargetActivity() {
        try {
            Class<?> localClass = getClassLoader().loadClass(mClass);
            Constructor<?> localConstructor = localClass.getConstructor(new Class[] {});
            Object instance = localConstructor.newInstance(new Object[] {});
            mPluginActivity = (DLPlugin) instance;

            ((DLAttachable) mProxyActivity).attach(mPluginActivity);
            // attach the proxy activity and plugin package to the mPluginActivity
            mPluginActivity.attach(mProxyActivity);

            Bundle bundle = new Bundle();
          //  bundle.putInt(DLConstants.FROM, DLConstants.FROM_EXTERNAL);
            mPluginActivity.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(mProxyActivity).createDexClassLoader("");
    }

}
