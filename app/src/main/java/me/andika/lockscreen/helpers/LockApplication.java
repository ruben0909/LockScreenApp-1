package me.andika.lockscreen.helpers;

import android.app.Application;

public class LockApplication extends Application {

   public boolean lockScreenShow=false;
    public int notificationId=1989;

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
