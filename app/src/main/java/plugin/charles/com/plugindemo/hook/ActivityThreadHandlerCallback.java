package plugin.charles.com.plugindemo.hook;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * @author 17111980
 * @date 2017/12/11.
 */


public class ActivityThreadHandlerCallback implements Handler.Callback {
    private Handler handler;


    public ActivityThreadHandlerCallback(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean handleMessage(Message msg) {
        //代表ActivityThread mH中的launch_activity
        if(msg.what == 100){
            handleLaunchActivity(msg);
        }
        handler.handleMessage(msg);
        return true;
    }

    private void handleLaunchActivity(Message msg) {
        //ActivityClientRecord
        Object obj = msg.obj;
        try {
            Field intentField  = obj.getClass().getDeclaredField("intent");
            intentField .setAccessible(true);
            Intent proxyInent  = (Intent) intentField.get(obj);
            Intent realIntent = proxyInent.getParcelableExtra("oldIntent");
            if (realIntent != null) {
                proxyInent.setComponent(realIntent.getComponent());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
