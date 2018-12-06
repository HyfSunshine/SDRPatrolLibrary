package com.sdr.patrol;

import android.os.Bundle;
import android.view.View;

import com.sdr.patrol.base.BaseActivity;
import com.sdr.patrollib.PatrolLibrary;
import com.sdr.patrollib.PatrolUser;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
    }

    public void jumpPatrol(View view) {
        PatrolLibrary.start(getContext(), new PatrolUser("25", "刘晟", "12345678910", "sdhafjhdas1415452ad4s45f45d11"));
    }
}
