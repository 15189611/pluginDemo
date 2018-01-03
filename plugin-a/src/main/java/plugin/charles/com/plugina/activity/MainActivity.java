package plugin.charles.com.plugina.activity;

import android.os.Bundle;

import plugin.charles.com.plugina.R;
import plugin.charles.com.pluginlib.DLBasePluginActivity;

public class MainActivity extends DLBasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_plugin);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
