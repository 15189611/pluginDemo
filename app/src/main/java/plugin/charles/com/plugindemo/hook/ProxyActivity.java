package plugin.charles.com.plugindemo.hook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import plugin.charles.com.plugindemo.R;
import plugin.charles.com.plugindemo.plugin.DLAttachable;
import plugin.charles.com.plugindemo.plugin.DLPlugin;

public class ProxyActivity extends AppCompatActivity implements DLAttachable {

    private DLProxyImpl impl = new DLProxyImpl(this);
    protected DLPlugin mRemoteActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        impl.onCreate(getIntent());
        Log.e("Charles2","代理的onCreate");
    }

    @Override
    protected void onStart() {
        if(mRemoteActivity != null){
            mRemoteActivity.onStart();
        }
        super.onStart();
        Log.e("Charles2","代理的onStart");
    }

    @Override
    protected void onRestart() {
        if(mRemoteActivity != null){
            mRemoteActivity.onRestart();
        }
        super.onRestart();
        Log.e("Charles2","代理的onRestart");
    }

    @Override
    protected void onResume() {
        if(mRemoteActivity != null){
            mRemoteActivity.onResume();
        }
        super.onResume();
        Log.e("Charles2","代理的onResume");
    }

    @Override
    protected void onPause() {
        if(mRemoteActivity != null){
            mRemoteActivity.onPause();
        }
        super.onPause();
        Log.e("Charles2","代理的onPause");
    }

    @Override
    protected void onDestroy() {
        if(mRemoteActivity != null){
            mRemoteActivity.onDestroy();
        }
        super.onDestroy();
        Log.e("Charles2","代理的onDestroy");
    }

    @Override
    public void attach(DLPlugin proxyActivity) {
        mRemoteActivity = proxyActivity;
    }

}
