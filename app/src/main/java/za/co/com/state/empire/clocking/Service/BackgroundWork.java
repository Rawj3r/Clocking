package za.co.com.state.empire.clocking.Service;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.sensoro.beacon.kit.Beacon;
import com.sensoro.beacon.kit.BeaconManagerListener;
import com.sensoro.cloud.SensoroManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by empirestate on 3/2/16.
 */
public class BackgroundWork extends Application implements BeaconManagerListener{

    private static final String TAG = BackgroundWork.class.getSimpleName();
    private SensoroManager sensoroManager;
    String mBeaconUDID = "";
    public Date mInTime;
    public Date mOutTime;
    public static final int STATE_ENTER_DOOR = 1;
    public int mState = STATE_INIT;
    public static final int STATE_INIT = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        initSensorSDK();

        /**
         * Start SDK in Serevice
         */

        Intent intent = new Intent();
        intent.setClass(this, MyService.class);
        startService(intent);
    }

    /**
     * Initial Sensoro SDK
     */
    private void initSensorSDK() {
        sensoroManager = SensoroManager.getInstance(getApplicationContext());
        sensoroManager.setCloudServiceEnable(true);
        sensoroManager.setBeaconManagerListener(this);
    }


    /**
     * Start Sensoro SDK
     * @param
     */

    public void startSensorSDK(){
        mBeaconUDID = "";
        mState = STATE_INIT;
        Intent intent = new Intent("changeState");
        sendBroadcast(intent);
        try {
            sensoroManager.startService();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopSensoroSDK(){
        mBeaconUDID = "";
        mState = STATE_INIT;
        Intent intent = new Intent("changeState");
        sendBroadcast(intent);
        try {
            sensoroManager.stopService();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Check whether bluetooth enabled.
     * @return
     */

    public boolean isBlueteethEnabled(){
        return sensoroManager.isBluetoothEnabled();
    }

    @Override
    public void onNewBeacon(Beacon beacon) {

        Log.e(TAG, beacon.getProximityUUID());
        String beaconUUID = beacon.getProximityUUID();

        if (beaconUUID.equals(mBeaconUDID)){
            if (mState == STATE_INIT){
                mState = STATE_ENTER_DOOR;
            }else {
                mState = STATE_INIT;
            }
        }


        mBeaconUDID = beaconUUID;
        Intent intent = new Intent("changeState");
        sendBroadcast(intent);

        Intent intent1 = new Intent(this, TransActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("type", 0);
        startActivity(intent1);
    }

    @Override
    public void onGoneBeacon(Beacon beacon) {

    }

    @Override
    public void onUpdateBeacon(ArrayList<Beacon> arrayList) {

    }
}
