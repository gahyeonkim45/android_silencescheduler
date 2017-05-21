package com.example.allactivity;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by youngchae on 2015-11-20.
 */
public class StarterApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "JoeN2cwfbPlaVxRx1OQxtYDxtEotggSJScFWdPEp", "Z6npF0JurHYe37fE035xv9XfeRMjlCdvCgQgVJgp");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }


}
