package com.capton.rongim;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.capton.common.base.BaseActivity;
import com.capton.ep.EasyPermission;
import com.capton.rongim.databinding.ActivityMainBinding;

import org.simple.eventbus.EventBus;


public class MainActivity extends BaseActivity<ActivityMainBinding> {


    @Override
    public String[] getPermissions() {
        return new String[]{PERMISSION_CAMERA};
    }

    @Override
    public EasyPermission.OnPermissionListener getPermissionListener() {
        return new EasyPermission.OnPermissionListener() {
            @Override
            public void onPermissionDenied(String s) {
                if (s.equals(PERMISSION_CAMERA)){

                }else if(s.equals(PERMISSION_CALL_PHONE)){

                }
            }

            @Override
            public void onPermissionGranted(String s) {

            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void clickMore() {

    }


    @Override
    public void clickRightText() {

     }
      @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          EventBus.getDefault().register(this);

     }

     @Override
    protected void yourOperation() {
         setShowActionBar(false);
    }



    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
