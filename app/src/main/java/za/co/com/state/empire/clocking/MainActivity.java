package za.co.com.state.empire.clocking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sensoro.beacon.kit.Beacon;
import com.sensoro.beacon.kit.BeaconManagerListener;
import com.sensoro.cloud.SensoroManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                }

                @Override
                public void onGoneBeacon(Beacon beacon) {

                }

                @Override
                public void onUpdateBeacon(ArrayList<Beacon> arrayList) {

                }
            };
        }
    }
}
