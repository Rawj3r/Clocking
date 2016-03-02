package za.co.com.state.empire.clocking.Service;

import android.app.Application;
import android.content.Intent;

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

    @Override
    public void onCreate() {
        super.onCreate();
        initSensorSDK();

        /**
         * Start SDK in Serevice
         */

        Intent intent = new Intent();
        intent.setClass(this, MyService.class);
    }

    private void initSensorSDK() {

    }

    @Override
    public void onNewBeacon(Beacon beacon) {

    }

    @Override
    public void onGoneBeacon(Beacon beacon) {

    }

    @Override
    public void onUpdateBeacon(ArrayList<Beacon> arrayList) {

    }
}
