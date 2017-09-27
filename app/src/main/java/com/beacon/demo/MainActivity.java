package com.beacon.demo;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements BeaconConsumer {

    ListView beaconList;
    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconList = (ListView) findViewById(R.id.beacon_list);

        beaconManager = BeaconManager.getInstanceForApplication(this);

        // Detect the main identifier (UID) frame:
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));

        // Detect the telemetry (TLM) frame:
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_TLM_LAYOUT));

        // Detect the URL frame:
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));

        beaconManager.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    final ArrayList<BeaconModel> list = new ArrayList<BeaconModel>();

                    Iterator<Beacon> it = beacons.iterator();

                    while (it.hasNext()) {
                        Beacon beacon = it.next();

                        BeaconModel model = new BeaconModel();

                        if (beacon.getServiceUuid() == 0xfeaa)
                        {
                            // This is Eddystone, which uses a service Uuid of 0xfeaa
                            Identifier eddystoneNamespaceId = beacon.getId1();
                            Identifier eddystoneInstanceId = beacon.getId2();
                        }
                        else
                        {

                        }


                        model.setBeaconName("Rssi: " + beacon.getRssi() + " Power: " + beacon.getTxPower());
                        model.setDistance("Dist: " + beacon.getDistance());

                        list.add(model);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            CustomBeaconAdapter adapter = new CustomBeaconAdapter(list, MainActivity.this);
                            beaconList.setAdapter(adapter);
                        }
                    });
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
