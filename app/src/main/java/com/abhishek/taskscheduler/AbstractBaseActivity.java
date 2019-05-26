package com.abhishek.taskscheduler;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Class to put some common code related to activities
 */
public abstract class AbstractBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    /**
     * Method to set the status bar color or lollipop and above devices
     *
     * @param colorCode
     */
    protected void setStatusBarColor(int colorCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorCode);
        }
    }

    /**
     * Method to show/hide the keyboard
     *
     * @param show
     * @param editText
     */
    protected void showSoftKeyboard(boolean show, EditText editText) {
        InputMethodManager inputManager
                = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (show) {
            inputManager.showSoftInput(editText, 0);
        } else {
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }


    /**
     * -------------------------------------- Permission handling --------------------------------------
     */
    private int permissionRequestCode;
    private Object extras;
    protected final int REQUEST_MARSHMELLO_PERMISSIONS = 10001;
    protected final int REQUEST_CAMERA_PERMISSION = 22222;
    protected boolean haveAllPermissions = false;

    public String[] mustPermissions =
            {
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.CAMERA,
            };

    protected boolean havePermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    protected boolean checkPermission(int requestCode, String permission, Object extras) {
        this.permissionRequestCode = requestCode;
        this.extras = extras;
        //if we have permission then will procceed
        if (havePermission(permission)) {
            return true;
        }
        //else we will take the permission
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            Toast.makeText(this, "Please provide this permission.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);

        return false;
    }

    protected void requestAllMustPermission() {
        checkPermissions(REQUEST_MARSHMELLO_PERMISSIONS, mustPermissions, null);
    }

    protected boolean checkPermissions(int requestCode, String[] permission, Object extras) {
        this.permissionRequestCode = requestCode;
        this.extras = extras;
        ArrayList<String> permissionsStr = new ArrayList<>();

        for (int i = 0; i < permission.length; i++) {
            if (!havePermission(permission[i])) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission[i])) {
                    Toast.makeText(this, "Please provide this permission.", Toast.LENGTH_LONG).show();
                }
                permissionsStr.add(permission[i]);
            }
        }


        if (permissionsStr.size() > 0) {
            String[] needTopermissionArray = permissionsStr.toArray(new String[permissionsStr.size()]);
            ActivityCompat.requestPermissions(this, needTopermissionArray, requestCode);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //System.out.println(">>>>>>>>>>>>>>>>> onRequestPermissionsResult " + requestCode + " grantResults " + grantResults.length);
        if (requestCode == permissionRequestCode) {

            if (grantResults.length >= 1) {
                boolean anyDenied = false;
                for (int i = 0; i < grantResults.length; i++) {
                    //System.out.println(">>>>>>>>>>>>>>>> grantResults " + grantResults[i]);
                    // Check if the only required permission has been granted
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "PERMISSION OF " + permissions[i] + " IS GRANTED.", Toast.LENGTH_SHORT).show();

                    } else {
                        anyDenied = true;
//                        Toast.makeText(this, "PERMISSION OF " + permissions[i] + " IS DENIED.", Toast.LENGTH_SHORT).show();
                    }
                }
                if (anyDenied) {
                    haveAllPermissions = false;
                    onPermissionResult(requestCode, false, extras);
                } else {
                    haveAllPermissions = true;
                    onPermissionResult(requestCode, true, extras);
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public abstract void onPermissionResult(int requestCode, boolean isGranted, Object extras);
}
