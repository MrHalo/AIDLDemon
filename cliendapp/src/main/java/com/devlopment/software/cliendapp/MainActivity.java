package com.devlopment.software.cliendapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devlopment.software.aidldemon.IAddtionInterface;
import com.devlopment.software.aidldemon.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnection {


    private static final String TAG = MainActivity.class.getSimpleName();
    private IAddtionInterface mInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initService();
    }

    private void initService() {
        Intent intent = new Intent("aidl_remote_service");
        intent.setPackage("com.devlopment.software.aidldemon");
        // 1.绑定服务
        boolean ret = bindService(intent, this, Context.BIND_AUTO_CREATE);
        Toast.makeText(this, "是否绑定成功： " + ret, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        mInterface = IAddtionInterface.Stub.asInterface(service);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add_person:
                try {
                    mInterface.addPerson(new Person("zhangsan", 15));
                    Toast.makeText(this, "添加人员", Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.get_person_list:

                try {
                    List<Person> personList = mInterface.getPersonList();
                    Log.d(TAG, "PersonList: " + personList.toString());
                    Toast.makeText(this, "获取personList", Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
