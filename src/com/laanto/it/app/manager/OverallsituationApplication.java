package com.laanto.it.app.manager;


import com.laanto.it.app.view.MainActivity;

import android.app.Application;

public class OverallsituationApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    public MainActivity mainActivity;

}
