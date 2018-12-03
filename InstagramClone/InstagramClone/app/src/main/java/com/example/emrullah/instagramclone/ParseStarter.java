package com.example.emrullah.instagramclone;

import android.app.Application;
import com.parse.Parse;

public class ParseStarter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("bWtQQEsrUNG2oKgO0vHmLiyqtSaSwbxEx5ESG9s3")
                // if desired
                .clientKey("L1JSPxzOiCt2NBygtPho14TbNgydV9opU19dx853")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
