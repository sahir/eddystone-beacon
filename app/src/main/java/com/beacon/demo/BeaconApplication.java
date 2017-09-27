package com.beacon.demo;

import android.app.Application;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.Collection;

/**
 * Created by Dheeraj on 4/20/2017.
 */

public class BeaconApplication extends Application implements BootstrapNotifier, BeaconConsumer {

    BeaconManager beaconManager;
    public static String TAG = "BeaconApplication";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void initiateBeaconService() {
        beaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());

        // Detect the main identifier (UID) frame:
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        // Detect the telemetry (TLM) frame:
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT));

        // Detect the URL frame:
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));

        //beaconManager.setDebug(true);

        beaconManager.setBackgroundScanPeriod(1100l);
        beaconManager.setBackgroundBetweenScanPeriod(30000l);

        beaconManager.bind(this);
    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d(TAG, "Got a didEnterRegion call");
    }

    @Override
    public void didExitRegion(Region region) {
        Log.d(TAG, "Got a didExitRegion call");
    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("com.beacon.demo", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
