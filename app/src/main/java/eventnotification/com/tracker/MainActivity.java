package eventnotification.com.tracker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import tracker.LocationTracker;
import utils.MessageUtil;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_SMS = 98;
    private static final int ALL_PERMISSIONS = 100;
    private static final int PLACE_PICKER = 1;
    private Button button;
    private LocationManager locationManager;
    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, ALL_PERMISSIONS);
        invokeMap();
    }

    private void invokeMap() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(MainActivity.this);
                    startActivityForResult(intent, PLACE_PICKER);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationTracker = new LocationTracker(place, MainActivity.this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationTracker);

        }
    }

    public void performAction() {
        MessageUtil messageUtil = new MessageUtil();
        messageUtil.performAction(getApplicationContext(), MainActivity.this);
        locationManager.removeUpdates(locationTracker);
    }
}

