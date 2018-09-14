package ru.agrass.testlitebox;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class ApplicationLiteBox extends Application {

    private static ApplicationLiteBox Instance;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeLeakDetection();
        Instance = this;
    }

    public static synchronized ApplicationLiteBox getInstance() {
        return Instance;
    }

    private void initializeLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
