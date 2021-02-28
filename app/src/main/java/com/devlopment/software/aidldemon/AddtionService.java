package com.devlopment.software.aidldemon;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AddtionService extends Service {
    public static final String TAG = AddtionService.class.getSimpleName();
    private List<Person> mPersonList;

    private AddtionBinder mBinder;

    private class AddtionBinder extends IAddtionInterface.Stub {
        @Override
        public int add(int val1, int val2) throws RemoteException {
            return val1 + val2;
        }

        @Override
        public boolean addPerson(Person person) throws RemoteException {
            if (person == null) return false;
            mPersonList.add(person);
            Log.d(TAG, "server： add person successfully, person: " + person.toString());
            return true;
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            Log.d(TAG, "server： getPersonList function" + mPersonList.toString());
            return mPersonList;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mBinder = new AddtionBinder();
        mPersonList = new ArrayList<>();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.w(TAG, "解除绑定");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mBinder = null;
    }
}
