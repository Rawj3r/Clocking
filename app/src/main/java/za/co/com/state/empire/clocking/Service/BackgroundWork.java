package za.co.com.state.empire.clocking.Service;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.sensoro.beacon.kit.Beacon;
import com.sensoro.beacon.kit.BeaconManagerListener;
import com.sensoro.cloud.SensoroManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Roger Nkosi on 3/2/16.
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


    }

    @Override
    public void onGoneBeacon(Beacon beacon) {
        Log.e(TAG, "gone");

        mState = STATE_INIT;
    }

    @Override
    public void onUpdateBeacon(ArrayList<Beacon> arrayList) {
        for (int i = 0; i < arrayList.size();  i++){
            Beacon beacon = arrayList.get(i);
            updateView(beacon);
        }
    }

    private void updateView(Beacon beacon) {
        if (beacon == null){
            return;
        }

        DecimalFormat format = new DecimalFormat(" #");
        String distance = format.format(beacon.getAccuracy() *100);
        Log.e(beacon.getSerialNumber(), "" + distance + " cm");
    }
}
