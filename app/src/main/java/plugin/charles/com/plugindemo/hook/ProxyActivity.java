package plugin.charles.com.plugindemo.hook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import plugin.charles.com.plugindemo.R;
import plugin.charles.com.plugindemo.plugin.DLAttachable;
import plugin.charles.com.plugindemo.plugin.DLPlugin;

public class ProxyActivity extends AppCompatActivity implements DLAttachable {

    private DLProxyImpl impl = new DLProxyImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        impl.onCreate(getIntent());
        Log.e("Charles2","代理的onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Charles2","代理的onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Charles2","代理的onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Charles2","代理的onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Charles2","代理的onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Charles2","代理的onDestroy");
    }

    @Override
    public void attach(DLPlugin proxyActivity) {

    }
}
