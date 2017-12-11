package plugin.charles.com.plugindemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Charles2","plugin--onCreate");
        setContentView(R.layout.activity_other);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Charles2","plugin--onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Charles2","plugin--onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Charles2","plugin--onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Charles2","plugin--onDestroy");
    }
}
