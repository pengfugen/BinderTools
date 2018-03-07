package wind.pfg.bindertools;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.IntDef;

import java.io.ByteArrayOutputStream;

public class PicService extends Service {

    private static String TAG = "BinderTools/PicService";
    ICallBack mRemote;

    public PicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log(TAG, "onCreate========");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.log(TAG, "onStartCommand========");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        LogUtil.log(TAG, "onBind========pid:"+ Process.myPid()+" tid:"+Process.myTid());
        return new PicServer();
    }

    class PicServer extends IPicServer.Stub {

        @Override
        public PicData getPicture(String path) {
            PicData picdata;
            String name;
            int size;
            byte [] data;
            /*//LogUtil.log(TAG, "getPicture========pid:"+ Process.myPid()+" tid:"+Process.myTid());
            //LogUtil.log(TAG, "getPicture======== before clear pid:"+ Binder.getCallingPid());
            //long origid = Binder.clearCallingIdentity();
            //LogUtil.log(TAG, "getPicture======== after clear pid:"+ Binder.getCallingPid());
            Binder.restoreCallingIdentity(origid);
            LogUtil.log(TAG, "getPicture======== restore pid:"+ Binder.getCallingPid());*/

            if("test_01".equals(path)) {
                name = "test_01.png";
                Bitmap bitmap = BitmapFactory.decodeResource(PicService.this.getResources(), R.drawable.test_01);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                data = baos.toByteArray();
                LogUtil.log(TAG, "getPicture(test_01)======== size:"+data.length);
                picdata = new PicData(name, data.length, data);
            } else {
                name = "test_02.png";
                Bitmap bitmap = BitmapFactory.decodeResource(PicService.this.getResources(), R.drawable.test_02);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                data = baos.toByteArray();
                LogUtil.log(TAG, "getPicture(test_02)======== size:"+data.length);
                picdata = new PicData(name, data.length, data);
            }
            return picdata;
        }

        @Override
        public void setCallBack(ICallBack callback) throws RemoteException {
            mRemote = callback;
            LogUtil.log(TAG, "setCallBack sucess! mRemote:"+mRemote);
            myHandler.sendMessage(myHandler.obtainMessage(1));
        }
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                LogUtil.log(TAG, "handleMessage");
                mRemote.onStateChanged(1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
}
