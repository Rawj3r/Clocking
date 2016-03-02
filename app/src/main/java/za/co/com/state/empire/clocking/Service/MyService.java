package za.co.com.state.empire.clocking.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;


/**
 * Created by Roger Nkosi on 3/2/16.
 */
public class MyService extends Service {

    private static final String TAG = MyService.class.getSimpleName();

    private BackgroundWork backgroundWork;
    private BluetoothBroadcastReceiver bluetoothBroadcastReceiver;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        backgroundWork = (BackgroundWork) getApplication();
        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();

        registerReceiver(bluetoothBroadcastReceiver, new IntentFilter(Constant.BLE_STATE_CHANGED_ACTION));

        if (((BackgroundWork) getApplication()).isBlueteethEnabled()){
            ((BackgroundWork) getApplication()).startSensorSDK();
        }else {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothBroadcastReceiver);
    }

    private class BluetoothBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.BLE_STATE_CHANGED_ACTION)){
                if (backgroundWork.isBlueteethEnabled()){
                    backgroundWork.startSensorSDK();
                }else {
                    backgroundWork.stopSensoroSDK();
                }
            }
        }
    }
}
