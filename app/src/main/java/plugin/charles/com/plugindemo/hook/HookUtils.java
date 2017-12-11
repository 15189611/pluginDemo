package plugin.charles.com.plugindemo.hook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 666 on 2017/12/6.
 */

public class HookUtils {
    private Class<?> proxyActivity;

    private Context context;

    public HookUtils(Class<?> proxyActivity, Context context) {
        this.proxyActivity = proxyActivity;
        this.context = context;
    }

    public void hookAms() {

        //一路反射，直到拿到IActivityManager的对象
        try {
            Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
            Field defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault");
            defaultFiled.setAccessible(true);
            Object defaultValue = defaultFiled.get(null);
            //反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //到这里已经拿到ActivityManager对象
            Object iActivityManagerObject = mInstance.get(defaultValue);


            //开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海
            Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");

            AmsInvocationHandler handler = new AmsInvocationHandler(iActivityManagerObject);

            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{IActivityManagerIntercept}, handler);

            //现在替换掉这个对象
            mInstance.set(defaultValue, proxy);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class AmsInvocationHandler implements InvocationHandler {

        private Object iActivityManagerObject;

        private AmsInvocationHandler(Object iActivityManagerObject) {
            this.iActivityManagerObject = iActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Log.i("HookUtil", method.getName());
            //我要在这里搞点事情
            if ("startActivity".contains(method.getName())) {
                Log.e("HookUtil", "Activity已经开始启动");
                Log.e("HookUtil", "小弟到此一游！！！");

                //替换Intent 先找到原始的intent,然后直接伪造一个Intent 给AMS
                Intent intent = null;
                int index = 0;
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof Intent) {
                        //说明找到了startActivity的Intent参数
                        intent = (Intent) args[i];
                        //这个意图是不能被启动的，因为Acitivity没有在清单文件中注册
                        index = i;
                    }
                }

                Intent proxyIntent = new Intent();
                ComponentName componentName = new ComponentName(context, proxyActivity);
                proxyIntent.setComponent(componentName);
                proxyIntent.putExtra("oldIntent", intent);
                args[index] = proxyIntent;
            }

            return method.invoke(iActivityManagerObject, args);
        }
    }

    public void hookSystemHandler(){
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            //获取主线程对象
            Object activityThread  = currentActivityThread.invoke(null);

            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            Handler handler  = (Handler) mH.get(activityThread);
            Field mCallback = Handler.class.getField("mCallback");
            mCallback.setAccessible(true);
            //设置自己实现的CallBack
            mCallback.set(handler,new ActivityThreadHandlerCallback(handler));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


}
