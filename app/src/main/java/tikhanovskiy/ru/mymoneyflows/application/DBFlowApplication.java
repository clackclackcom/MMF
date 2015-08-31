package tikhanovskiy.ru.mymoneyflows.application;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

public class DBFlowApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
