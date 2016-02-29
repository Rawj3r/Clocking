package za.co.com.state.empire.clocking;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.sensoro.beacon.kit.Beacon;
import com.sensoro.beacon.kit.BeaconManagerListener;
import com.sensoro.cloud.SensoroManager;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ENABLE_BT = 100;
    public static final String YunziSN = "0117C5393A7A";
    private static final String TAG = MainActivity.class.getSimpleName();
    String mBeaconUDID = "";

    public static final int STATE_INIT = 0;
    public static final int STATE_ENTER_DOOR = 1;
    public static final int STATE_IN_ROOM = 2;
    public static final int STATE_OUT_ROOM_ENTER_DOOR = 3;
    public Date mInTime;
    public Date mOutTime;
    public int mState = STATE_INIT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeSDK();
    }

    public void initializeSDK() {
        SensoroManager sensoroManager = SensoroManager.getInstance(this);
        /**
         * Check whether the Bluetooth is on
         **/
        if (sensoroManager.isBluetoothEnabled()) {
            /**
             * Enable cloud service (upload sensor data, including battery status, UMM, etc.)。Without setup, it keeps in closed status as default.
             **/
            sensoroManager.setCloudServiceEnable(true);
            /**
             * Enable SDK service
             **/
            try {
                sensoroManager.startService();
            } catch (Exception e) {
                e.printStackTrace(); // Fetch abnormal info
            }

            sensoroManager.addBroadcastKey("0117C5393A7A");
            BeaconManagerListener beaconManagerListener = new BeaconManagerListener() {

                @Override
                public void onNewBeacon(Beacon beacon) {
                    // New Senso found
                    if (beacon.getSerialNumber().equals(YunziSN)){
                        System.out.println("New Beacon");
                    }

                    Log.e(TAG, beacon.getProximityUUID());

                    String beaconUUID = beacon.getProximityUUID();
                    if (beaconUUID.equals(mBeaconUDID)){
                        if (mState == STATE_INIT){
                            mState = STATE_ENTER_DOOR;
                        }else if (mState == STATE_IN_ROOM){
                            mState = STATE_OUT_ROOM_ENTER_DOOR;
                            mOutTime = new Date();
                            Log.e(TAG, "out room");
                        }
                    }

                }

                @Override
                public void onGoneBeacon(Beacon beacon) {

                }

                @Override
                public void onUpdateBeacon(ArrayList<Beacon> arrayList) {

                }
            };
            sensoroManager.setBeaconManagerListener(beaconManagerListener);
        }else {
            Intent blue = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(blue, REQUEST_ENABLE_BT);
        }
    }
}
