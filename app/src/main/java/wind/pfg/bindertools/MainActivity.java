package wind.pfg.bindertools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Process;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends Activity  implements View.OnClickListener{

    private static String TAG = "BinderTools/MainActivity";

    Button btn;
    ImageView imageView;
    PicServiceConnection mConnection;
    boolean isBinded = false;
    int []arr;
    MyCallBack mCallBack;
    Object obj = new Object();

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg != null && msg.what == 1) {
                /*PicData data = (PicData) msg.obj;
                LogUtil.log(TAG, "handleMessage========");
                if(data != null) {
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(data.mData,0,data.mData.length));
                }*/

                IPicServer remote = (IPicServer) msg.obj;
                LogUtil.log(TAG, "handleMessage========pid:"+Process.myPid()+" tid:"+Process.myTid());
                // setCallBack因为是向Server端添加监听器，Server在添加时监听就立刻调用onStateChanged回调给Client端。
                // 假如回调时Server端线程被阻塞了(直接在Server的setCallBack sleep几秒)，Client会接着运行下面的for循环吗？
                // 还是要等Server回调Client的onStateChanged执行完再执行for循环？Client会有什么表现？
                // 验证结果：
                // 1:假如Server端的setCallBack
                try {
                    arr = new int[2];
                    remote.setCallBack(mCallBack);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    LogUtil.log(TAG, "RemoteException e:"+e.getMessage());
                }
                for(int i = 0; i< 10; i++) {
                    LogUtil.log(TAG, "i:"+i);
                }
                // 下述代码模拟arr还没创建时，执行MyCallBack.onStateChanged方法时会遇到arr为null的空指针异常，这时会有PicServie发生crash以及MainActivity这边发生ANR现象产生。
                // 有两部分代码,下述是代码片段1
                /*try {
                    synchronized (obj) {
                        obj.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LogUtil.log(TAG, "InterruptedException e:"+e.getMessage());
                }*/
                // arr = new int[2];
            } else {
                //LogUtil.log(TAG, "handleMessage success========");
                imageView.setImageBitmap(null);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        mConnection = new PicServiceConnection();
        mCallBack = new MyCallBack();
        LogUtil.log(TAG, "onCreate========pid:"+Process.myPid()+" tid:"+Process.myTid());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isBinded) {
            unbindService(mConnection);
            isBinded = false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //LogUtil.log(TAG, "onDestroy========");
    }

    class PicServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // 运行在主线程
            LogUtil.log(TAG, "onServiceConnected========pid:"+Process.myPid()+" tid:"+Process.myTid());

            IPicServer remote = IPicServer.Stub.asInterface(iBinder);
            try {
                PicData data = remote.getPicture("test_01");
                Message message = myHandler.obtainMessage(1);
                //message.obj = data;
                message.obj = remote;
                message.sendToTarget();
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtil.log(TAG, "onServiceConnected getPicture failed, exception:"+e.getMessage());
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtil.log(TAG, "onServiceDisconnected========");
        }
    }

    @Override
    public void onClick(View view) {
        //LogUtil.log(TAG, "onClick======== pid:"+Process.myPid()+" tid:"+Process.myTid());

        /*LogUtil.log(TAG, "onClick======== before clear pid:"+ Binder.getCallingPid());
        long origid = Binder.clearCallingIdentity();
        LogUtil.log(TAG, "onClick======== after clear pid:"+ Binder.getCallingPid());
        Binder.restoreCallingIdentity(origid);
        LogUtil.log(TAG, "onClick======== restore pid:"+ Binder.getCallingPid());*/
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("wind.pfg.bindertools", "wind.pfg.bindertools.PicService"));
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        isBinded = true;
       /* Intent intent2 = new Intent();
        intent2.setComponent(new ComponentName("wind.pfg.bindertools", "wind.pfg.bindertools.BackService"));
        startService(intent2);
        myHandler.sendMessageDelayed(myHandler.obtainMessage(2), 1000*60*10);*/
    }

    private class MyCallBack extends ICallBack.Stub {
        @Override
        public void onStateChanged(int value) throws RemoteException {
            // 不是运行在主线程
            // 当arr为null时居然是PicService进程会crash？
            LogUtil.log(TAG, "onStateChanged====== pid:"+Process.myPid()+" tid:"+Process.myTid());
            for(int i = 0; i < arr.length; i++) {

            }
            // 有两部分代码,下述是代码片段2
            /*synchronized (obj) {
                obj.notifyAll();
                LogUtil.log(TAG, "onStateChanged====== notifyAll");
            }*/
        }
    }
}
