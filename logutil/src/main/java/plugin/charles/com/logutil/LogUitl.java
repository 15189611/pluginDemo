package plugin.charles.com.logutil;

import android.util.Log;

/**
 * @author 17111980
 * @date 2017/11/30.
 */


public class LogUitl {
    public static final String TAG="LogUitl";
    private void  printLog(){
        Log.e(TAG,"这是来自另外一个dex中的log");
    }
}
