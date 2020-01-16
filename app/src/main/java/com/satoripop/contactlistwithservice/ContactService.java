package com.satoripop.contactlistwithservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

public class ContactService extends Service implements ContactListener {

    private ServiceHandler mServiceHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        initServiceHandler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message = mServiceHandler.obtainMessage();
        message.arg1 = startId;
        mServiceHandler.sendMessage(message);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChange() {
        //todo: refresh the list
    }

    private void initServiceHandler() {
        HandlerThread thread = new HandlerThread("ContactServiceArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Looper looper = thread.getLooper();
        mServiceHandler = new ServiceHandler(looper);
    }

    private void startContactObserver(){
        try {
            getApplication().getContentResolver()
                    .registerContentObserver(ContactsContract.Contacts.CONTENT_URI,
                            true,
                            new ContactObserver(new Handler(), this));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private final class ServiceHandler extends Handler {

        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            startContactObserver();
        }
    }
}
