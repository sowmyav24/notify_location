package tracker;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.places.Place;

import eventnotification.com.tracker.MainActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static eventnotification.com.tracker.MainActivity.MY_PERMISSIONS_REQUEST_LOCATION;
import static eventnotification.com.tracker.MainActivity.PACKAGE_NAME;

public class LocationTracker extends BroadcastReceiver {
    private MainActivity activity;
    private LocationManager locationManager;
    private Place place;
    private Context context;
    PendingIntent pendingIntent;

    public LocationTracker(MainActivity mainActivity, LocationManager locationManager, Place place, Context context) {
        this.activity = mainActivity;
        this.locationManager = locationManager;
        this.place = place;
        this.context = context;
        this.pendingIntent = PendingIntent.getBroadcast(context, -1, new Intent(PACKAGE_NAME), 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        boolean state = intent.getBooleanExtra(key, false);
        if (state) {
            activity.performAction();
            locationManager.removeProximityAlert(pendingIntent);
        }
    }

    public void startListening() {
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        }
        locationManager.addProximityAlert(place.getLatLng().latitude, place.getLatLng().longitude, 200, -1, pendingIntent);
    }
}