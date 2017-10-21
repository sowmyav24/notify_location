package actions;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Toast;

import tracker.LocationTracker;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER;
import static eventnotification.com.tracker.MainActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class MessageAction extends AppCompatActivity implements NotificationAction {

    private String contactNumber;
    private Context context;

    public MessageAction(Context context) {
        this.context = context;
    }

    @Override
    public void performAction() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(contactNumber, null, "This is a test message", null, null);
        Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onActivity(Intent intent, LocationManager locationManager, LocationTracker locationTracker) {
        String[] projection = {NUMBER};
        Cursor cursor = context.getContentResolver().query(intent.getData(), projection, null, null, null);
        cursor.moveToFirst();
        int numberColumnIndex = cursor.getColumnIndex(NUMBER);
        contactNumber = cursor.getString(numberColumnIndex);
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1000, 0, locationTracker);
    }
}