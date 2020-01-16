package com.satoripop.contactlistwithservice;

import android.database.ContentObserver;
import android.os.Handler;

public class ContactObserver extends ContentObserver {

    private ContactListener contactListener;

    ContactObserver(Handler handler, ContactListener listener) {
        super(handler);
        contactListener = listener;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        contactListener.onChange();
    }
}
