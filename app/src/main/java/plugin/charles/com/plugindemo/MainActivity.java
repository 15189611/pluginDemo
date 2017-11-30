package plugin.charles.com.plugindemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //动态加载dex文件  这里是个jar 包里面的方法
    public void start(View view) {
        //dex解压释放后的目录
        final File dexOutPutDir = getDir("dex", 0);
        //dex所在目录
        final String dexPath = Environment.getExternalStorageDirectory().toString() + File.separator + "new_log.jar";

        //第一个参数：是dex压缩文件的路径
        //第二个参数：是dex解压缩后存放的目录
        //第三个参数：是C/C++依赖的本地库文件目录,可以为null
        //第四个参数：是上一级的类加载器
        DexClassLoader classLoader = new DexClassLoader(dexPath, dexOutPutDir.getAbsolutePath(), null, getClassLoader());

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

}
