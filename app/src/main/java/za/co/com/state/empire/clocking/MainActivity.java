package za.co.com.state.empire.clocking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sensoro.cloud.SensoroManager;

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
        }
    }
}
