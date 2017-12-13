package plugin.charles.com.pluginlib;

import android.content.Intent;

/**
 * @author 17111980
 * @date 2017/12/13.
 */


public class DLIntent extends Intent {


    private String mPluginPackage;
    private String mPluginClass;

    public DLIntent() {
        super();
    }

    public DLIntent(String pluginPackage) {
        super();
        this.mPluginPackage = pluginPackage;
    }

    public DLIntent(String pluginPackage, String pluginClass) {
        super();
        this.mPluginPackage = pluginPackage;
        this.mPluginClass = pluginClass;
    }

    public DLIntent(String pluginPackage, Class<?> clazz) {
        super();
        this.mPluginPackage = pluginPackage;
        this.mPluginClass = clazz.getName();
    }

    public String getPluginPackage() {
        return mPluginPackage;
    }

    public void setPluginPackage(String mPluginPackage) {
        this.mPluginPackage = mPluginPackage;
    }

    public String getPluginClass() {
        return mPluginClass;
    }

    public void setPluginClass(String mPluginClass) {
        this.mPluginClass = mPluginClass;
    }
}
