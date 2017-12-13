package plugin.charles.com.plugindemo.plugin;

import android.os.Bundle;
import android.util.Log;

import plugin.charles.com.plugindemo.R;

public class OtherActivity extends DLBasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Charles2", "plugin--onCreate");
        that.setContentView(R.layout.activity_other);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Charles2", "plugin--onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Charles2", "plugin--onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Charles2", "plugin--onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Charles2", "plugin--onDestroy");
    }

}
