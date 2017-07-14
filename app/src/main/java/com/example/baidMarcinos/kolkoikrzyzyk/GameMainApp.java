package com.example.baidMarcinos.kolkoikrzyzyk;

import android.app.Application;

/**
 * Created by MARCIN on 2017-05-28.
 */


public class GameMainApp extends Application {

    private static boolean sIsChatActivityOpen = false;

    public static boolean isChatActivityOpen() {
        return sIsChatActivityOpen;
    }

    public static void setChatActivityOpen(boolean isChatActivityOpen) {
        GameMainApp.sIsChatActivityOpen = isChatActivityOpen;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}