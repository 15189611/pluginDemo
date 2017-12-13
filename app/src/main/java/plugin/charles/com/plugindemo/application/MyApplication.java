package plugin.charles.com.plugindemo.application;

import android.app.Application;

import plugin.charles.com.plugindemo.hook.HookUtils;
import plugin.charles.com.plugindemo.hook.ProxyActivity;

/**
 * Created by 666 on 2017/12/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*HookUtils hookUtil = new HookUtils(ProxyActivity.class, this);
        hookUtil.hookSystemHandler();
        hookUtil.hookAms();*/
    }

}
