package plugin.charles.com.plugindemo.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 * @author 17111980
 * @date 2017/12/12.
 */


public class DLBasePluginActivity extends Activity implements DLPlugin {
    /**
     * 代理activity，可以当作Context来使用，会根据需要来决定是否指向this
     */
    protected Activity mProxyActivity;
    /**
     * 等同于mProxyActivity，可以当作Context来使用，会根据需要来决定是否指向this<br/>
     * 可以当作this来使用
     */
    protected Activity that;
    private int type = 0;

    @Override
    public void attach(Activity proxyActivity) {
        mProxyActivity = (Activity) proxyActivity;
        that = mProxyActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (type == 1) {
            super.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        if (type == 1) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (type == 1) {
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onResume() {
        if (type == 1) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (type == 1) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (type == 1) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (type == 1) {
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

}
