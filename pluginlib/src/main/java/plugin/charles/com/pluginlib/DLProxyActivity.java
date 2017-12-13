package plugin.charles.com.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

import plugin.charles.com.pluginlib.internal.DLAttachable;
import plugin.charles.com.pluginlib.internal.DLPlugin;
import plugin.charles.com.pluginlib.internal.DLProxyImpl;

/**
 * @author 17111980
 * @date 2017/12/13.
 */


public class DLProxyActivity extends Activity implements DLAttachable {

    protected DLPlugin mRemoteActivity;
    private DLProxyImpl impl = new DLProxyImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        impl.onCreate(getIntent());
    }

    @Override
    public void attach(DLPlugin proxyActivity, PluginManager pluginManager) {
        mRemoteActivity = proxyActivity;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mRemoteActivity.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public AssetManager getAssets() {
        return impl.getAssets() == null ? super.getAssets() : impl.getAssets();
    }

    @Override
    public Resources getResources() {
        return impl.getResources() == null ? super.getResources() : impl.getResources();
    }

    @Override
    public Resources.Theme getTheme() {
        return impl.getTheme() == null ? super.getTheme() : impl.getTheme();
    }

    @Override
    protected void onStart() {
        mRemoteActivity.onStart();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        mRemoteActivity.onRestart();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        mRemoteActivity.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mRemoteActivity.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mRemoteActivity.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mRemoteActivity.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mRemoteActivity.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mRemoteActivity.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mRemoteActivity.onNewIntent(intent);
        super.onNewIntent(intent);
    }

    @Override
    public void onBackPressed() {
        mRemoteActivity.onBackPressed();
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return mRemoteActivity.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        return mRemoteActivity.onKeyUp(keyCode, event);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        mRemoteActivity.onWindowAttributesChanged(params);
        super.onWindowAttributesChanged(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mRemoteActivity.onWindowFocusChanged(hasFocus);
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mRemoteActivity.onCreateOptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mRemoteActivity.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
