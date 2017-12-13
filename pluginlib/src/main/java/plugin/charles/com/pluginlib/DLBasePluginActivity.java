package plugin.charles.com.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import plugin.charles.com.pluginlib.internal.DLPlugin;

/**
 * @author 17111980
 * @date 2017/12/13.
 */


public class DLBasePluginActivity extends Activity implements DLPlugin {
    private int mFrom;
    protected Activity that;
    protected Activity mProxyActivity;
    protected DLPluginPackage mDlPluginPackage;

    @Override
    public void attach(Activity proxyActivity, DLPluginPackage pluginPackage) {
        mProxyActivity = proxyActivity;
        that = mProxyActivity;
        mDlPluginPackage = pluginPackage;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFrom = savedInstanceState.getInt(DLConstants.FROM, DLConstants.FROM_INTERNAL);
        }

        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.onCreate(savedInstanceState);
            mProxyActivity = this;
            that = mProxyActivity;
        }

    }

    @Override
    public void setContentView(View view) {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.setContentView(view);
        } else {
            mProxyActivity.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.setContentView(view, params);
        } else {
            mProxyActivity.setContentView(view, params);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            mProxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.addContentView(view, params);
        } else {
            mProxyActivity.addContentView(view, params);
        }
    }

    @Override
    public View findViewById(int id) {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            return super.findViewById(id);
        } else {
            return mProxyActivity.findViewById(id);
        }
    }

    @Override
    public Intent getIntent() {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            return super.getIntent();
        } else {
            return mProxyActivity.getIntent();
        }
    }

    @Override
    public void onStart() {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onResume() {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom == DLConstants.FROM_INTERNAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (mFrom == DLConstants.FROM_INTERNAL) {
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
