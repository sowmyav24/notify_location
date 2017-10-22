package eventnotification.com.tracker;

import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import actions.MessageAction;
import tracker.LocationTracker;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.SEND_SMS;
import static android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int ALL_PERMISSIONS = 100;
    private static final int PLACE_PICKER = 1;
    private static final int CONTACT_PICKER = 2;
    public static final String PACKAGE_NAME = "tracker";
    private MessageAction messageAction;
    private LocationManager locationManager;
    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, SEND_SMS, READ_CONTACTS}, ALL_PERMISSIONS);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        invokeMap();
    }

    public void performAction() {
        messageAction.performAction();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER && resultCode == RESULT_OK) {
            locationPickerActivity(data);
        }
        if (requestCode == CONTACT_PICKER && resultCode == RESULT_OK) {
            contactsActivity(data);
        }
    }

    private void invokeMap() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(MainActivity.this);
            startActivityForResult(intent, PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void invokeContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, CONTACT_PICKER);
    }

    private void locationPickerActivity(Intent data) {
        Place place = PlacePicker.getPlace(this, data);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationTracker = new LocationTracker(MainActivity.this, locationManager, place, getApplicationContext());
        invokeContacts();
    }

    private void contactsActivity(Intent data) {
        registerReceiver(locationTracker, new IntentFilter(PACKAGE_NAME));
        messageAction = new MessageAction(getApplicationContext());
        messageAction.onActivity(data, locationManager, locationTracker);
    }
}

