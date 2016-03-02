package za.co.com.state.empire.clocking;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sensoro.beacon.kit.Beacon;
import com.sensoro.beacon.kit.BeaconManagerListener;
import com.sensoro.cloud.SensoroManager;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Login extends AppCompatActivity{

    public static String BEACON_SN = "0117C5393A7A";
    public static final int REQUEST_ENABLE_BT = 1283;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        beaconTest();

    }

    private void beaconTest(){
        final SensoroManager sensoroManager = SensoroManager.getInstance(this);

        if (sensoroManager.isBluetoothEnabled()){
            sensoroManager.setCloudServiceEnable(true);

            try {
                sensoroManager.startService();
            }catch (Exception e){
                e.printStackTrace();
            }

            sensoroManager.addBroadcastKey("feh6348437dh343874hd83743hd3dj9");
            BeaconManagerListener beaconManagerListener = new BeaconManagerListener() {
                @Override
                public void onNewBeacon(Beacon beacon) {
                    // new beacon found
                    if (beacon.getSerialNumber().equals(BEACON_SN)){
                        System.out.printf("%s", "New beacon" + BEACON_SN + "found");
                    }
                }

                @Override
                public void onGoneBeacon(Beacon beacon) {
                    System.out.println("Out of range......");
                }

                @Override
                public void onUpdateBeacon(ArrayList<Beacon> arrayList) {
                    //Refresh sensor info
                    for (int i = 0; i<arrayList.size(); i++){
                        Beacon beacon = arrayList.get(i);
                        updateView(beacon);
                    }
                }
            };

            sensoroManager.setBeaconManagerListener(beaconManagerListener);
        }else {
            Intent blueTeethIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(blueTeethIntent, REQUEST_ENABLE_BT);
        }
    }

    private void updateView(Beacon beacon){
        if (beacon == null){
            return;
        }

        DecimalFormat format = new DecimalFormat("#");
        String distance = format.format(beacon.getAccuracy() *100);
        System.out.println("" + distance + " cm");
    }
}