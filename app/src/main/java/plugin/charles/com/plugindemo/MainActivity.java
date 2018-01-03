package plugin.charles.com.plugindemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import plugin.charles.com.plugindemo.hook.PluginManager;
import plugin.charles.com.plugindemo.plugin.OtherActivity;
import plugin.charles.com.plugindemo.util.DLConstants;
import plugin.charles.com.pluginlib.DLIntent;
import plugin.charles.com.pluginlib.DLPluginPackage;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private PluginManager instance;

    private DLPluginPackage pluginPackage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.bg);
        instance = PluginManager.getInstance(this);
        final String path = Environment.getExternalStorageDirectory() + File.separator + "plugin-a-debug.apk" ;
        pluginPackage = plugin.charles.com.pluginlib.PluginManager.getInstance(this).loadApk(path);
    }

    //动态加载dex文件  这里是个jar 包里面的方法
    public void start(View view) {
        instance.dexJar();
    }

    private boolean mChange = false;
    private String skinType = "";
    private int APK_TYPE;

    /**
     * 替换添加皮肤apk:bg
     */
    public void replace(View view) {
        if (!mChange) {
            skinType = "skindemo-debug.apk";
            APK_TYPE = 1;
            mChange = true;
        } else {
            skinType = "skindemo2-debug.apk";
            APK_TYPE = 2;
            mChange = false;
        }

        final String path = Environment.getExternalStorageDirectory() + File.separator + skinType;
        final String pkgName = instance.getUninstallApkPkgName(this, path);

        instance.dynamicLoadApk(path, pkgName, APK_TYPE, linearLayout);

    }

    /**
     *  使用Hook思想（市场大部分用-->推荐）
     * hookActivity(启动一个无注册的activity)
     */
    public void hookActivity(View view) {
        Intent intent = new Intent(this, OtherActivity.class);
        intent.putExtra(DLConstants.EXTRA_CLASS, "plugin.charles.com.plugindemo.plugin.OtherActivity");
        intent.putExtra(DLConstants.EXTRA_PACKAGE, "plugin.charles.com.plugindemo");
        startActivity(intent);
    }

    /**
     * 使用DynamicLoadApk 代理思想
     * @param view
     */
    public void startPlugin(View view){
        plugin.charles.com.pluginlib.PluginManager instance = plugin.charles.com.pluginlib.PluginManager.getInstance(this);
        instance.startPluginActivity(this,new DLIntent(pluginPackage.packerName,pluginPackage.packageInfo.activities[0].name));
    }

}
