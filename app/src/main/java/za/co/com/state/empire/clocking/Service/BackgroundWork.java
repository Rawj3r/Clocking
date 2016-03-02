package za.co.com.state.empire.clocking.Service;

import android.app.Application;

import com.sensoro.beacon.kit.Beacon;
import com.sensoro.beacon.kit.BeaconManagerListener;

import java.util.ArrayList;

/**
 * Created by empirestate on 3/2/16.
 */
public class BackgroundWork extends Application implements BeaconManagerListener{

    private static final String TAG = BackgroundWork.class.getSimpleName();
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
