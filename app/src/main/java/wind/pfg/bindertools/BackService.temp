package wind.pfg.bindertools;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

public class BackService extends Service {
    private static String TAG = "BinderTools/BackService";

    public BackService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log(TAG, "onCreate====");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.log(TAG, "onStartCommand====");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
