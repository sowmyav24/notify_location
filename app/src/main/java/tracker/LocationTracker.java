package tracker;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

import eventnotification.com.tracker.R;
import utils.MessageUtil;

public class LocationTracker implements android.location.LocationListener {
    private Context context;
    private Place place;
    private Activity activity;
    private LocationManager locationManager;

    public LocationTracker(Context context, Place place, LocationManager locationManager, Activity activity) {
        this.context = context;
        this.place = place;
        this.locationManager = locationManager;
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
        getExactLocation(location);
    }

    private void getExactLocation(Location location) {
        DecimalFormat df = new DecimalFormat("#.####");
        double currentLat = Double.valueOf(df.format(location.getLatitude()));
        double currentLng = Double.valueOf(df.format(location.getLongitude()));
        LatLng latLng = place.getLatLng();
        double destinationLat = Double.valueOf(df.format(latLng.latitude));
        double destinationLng = Double.valueOf(df.format(latLng.longitude));
        if (destinationLat == currentLat && destinationLng == destinationLng) {
            sendMessage(currentLat, currentLng);
        }
    }

    private void sendMessage(double latitude, double longitude) {
        TextView textView = activity.findViewById(R.id.text);
        MessageUtil.sendMessage(context, textView.getText().toString());
        Toast.makeText(context, "Latitude " + latitude + "Longitude " + longitude, Toast.LENGTH_LONG).show();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}