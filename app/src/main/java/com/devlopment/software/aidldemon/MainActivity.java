package com.devlopment.software.aidldemon;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * AIDL实现步骤：
 * 1.创建AIDL文件，写入抽象方法
 * 2.rebuild工程，系统
 *
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private AdditionServiceConnected connected;

    private EditText value1ET;
    private EditText value2ET;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        value1ET = findViewById(R.id.value1);
        value2ET = findViewById(R.id.value2);
        result = findViewById(R.id.result);
        initService();
    }

    private void initService() {
        connected = new AdditionServiceConnected();
        Intent intent = new Intent("aidl_remote_service");
        intent.setPackage("com.devlopment.software.aidldemon");
        // 1.绑定服务
        boolean ret = bindService(intent, connected, Context.BIND_AUTO_CREATE);
        Toast.makeText(this, "是否绑定成功： " + ret, Toast.LENGTH_SHORT).show();
    }


    IAddtionInterface additionService;


    class AdditionServiceConnected implements ServiceConnection{
        // 2.绑定成功回调
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // Stub是继承IBinder并实现IaddtionService
            additionService = IAddtionInterface.Stub.asInterface(service);
            Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_SHORT).show();
        }

        // 3.绑定失败回调
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service disconnected", Toast.LENGTH_SHORT).show();
        }
    }


    public void onClick(View view) {

        int value1 = Integer.parseInt(value1ET.getText().toString());
        int value2 = Integer.parseInt(value2ET.getText().toString());
        int res = 0;
        if (additionService != null) {
            try {
                res = additionService.add(value1, value2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG, "res = " + res);
        result.setText(String.valueOf(res));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connected);
    }
}
